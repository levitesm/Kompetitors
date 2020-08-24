package fr.ippon.kompetitors.service;

import fr.ippon.kompetitors.domain.Competitors;
import fr.ippon.kompetitors.domain.Legal;
import fr.ippon.kompetitors.domain.SocieteMain;
import fr.ippon.kompetitors.repository.CompetitorsRepository;
import fr.ippon.kompetitors.repository.LegalRepository;
import fr.ippon.kompetitors.repository.SocieteMainRepository;
import fr.ippon.kompetitors.web.rest.CompetitorsResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

import org.xnio.streams.ReaderInputStream;

import java.io.StringReader;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class SocieteService {
    private final LegalRepository legalRepository;
    private final RestTemplate restTemplate;
    private final CompetitorsRepository competitorsRepository;
    private final SocieteMainRepository societeMainRepository;

    private final String TOKEN="bc92cd0ff02bf740eba4483fefdc0cb1";

    private final Logger log = LoggerFactory.getLogger(CompetitorsResource.class);


    public SocieteService(LegalRepository legalRepository, RestTemplate restTemplate, CompetitorsRepository competitorsRepository, SocieteMainRepository societeMainRepository) {
        this.legalRepository = legalRepository;
        this.restTemplate = restTemplate;
        this.competitorsRepository = competitorsRepository;
        this.societeMainRepository = societeMainRepository;
    }


    public void updateLegalInfo(Long legalId) {

        //Get Current Legal Info
        legalRepository.findById(legalId).ifPresent(legal -> {
            Legal legalInfo = legal;

            //Get Siren
            String siren = legalInfo.getSiren();

            log.debug(">>>>>    TRY call Societe.com API with SIREN = "+siren +" <<<<<<");

            //Call API with SIREN
            Main resLegalInfo=null;
            try {
                ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.societe.com/pro/dev/societe/"+siren+"?token="+TOKEN,
                    HttpMethod.GET,
                    createRequest(),//"method", dataJson),
                    String.class
                );

                log.debug(">>>>> API CALL CODE = " + response.getStatusCode() + " <<<<<");

                if (response.getStatusCode() != HttpStatus.OK) {
                    log.debug(">>> SIREN not found in Societe <<<");
                    return;
                }
                if (response.getBody() == null){
                    log.debug(">>> Empty response body from Societe <<<");
                    return;
                }


                String resStr = response.getBody().toString();
                JAXBContext context = JAXBContext.newInstance(Main.class);
                Unmarshaller um = context.createUnmarshaller();
                resLegalInfo = (Main)um.unmarshal(new ReaderInputStream(new StringReader(resStr)));

            } catch (Exception e) {
                //error
                log.debug("!!!!! >>>>>>>" + e.getMessage());
                return;
            }


            //Change fields in legalInfo
            //legalInfo.setActivity(resLegalInfo.apetexte);

            //String[] dates = resLegalInfo.dateimmat.split("-");
            //legalInfo.setFounded(LocalDate.of(Integer.parseInt(dates[2]),Integer.parseInt(dates[1]),Integer.parseInt(dates[0])));

            legalInfo.setLegalAddress(resLegalInfo.adresse);

            legalInfo.setGreffe(resLegalInfo.greffe);


            //SAVE Fixed Legal Info
            legalRepository.save(legalInfo);


        });

    }

    private HttpEntity<MultiValueMap<String, String>> createRequest () {
        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new HttpEntity<>(requestMap, headers);
    }


    @XmlRootElement
    private static class Main {


        @XmlElement
        private String no;

        @XmlElement
        private String deno;

        @XmlElement
        private String greffe;

        @XmlElement
        private String enseigne;

        @XmlElement
        private String psiret;

        @XmlElement
        private String adresse;

        @XmlElement
        private String codepostal;

        @XmlElement
        private String normcommune;

        @XmlElement
        private String commune;

        @XmlElement
        private String ape;

        @XmlElement
        private String apetexte;

        @XmlElement
        private String dateimmat;

        @XmlElement
        private String dcren;

        @XmlElement
        private String nationalite;

        @XmlElement
        private String formejur;

        @XmlElement
        private Cap capital;



        @XmlElement
        private String url;


        public Main() {

        }


    }

    @XmlAccessorType(XmlAccessType.FIELD)
    private static class Cap {

        @XmlAttribute
        private String devisecap;

        @XmlAttribute
        private String typecap;

        @XmlValue
        private String value;
    }



    // @Scheduled(fixedRate = 604800000) //Every Week
    public void updateSocieteMainInfo() {

        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug(" ");
        log.debug("                           ENTERING SOCIETE.COM UPDATE");
        log.debug(" ");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        List<Competitors> competitors = competitorsRepository.findAll();

        for (Competitors c: competitors) {
            if(c.getLegal()==null) continue;
            if(c.getLegal().iterator().next().getSiren()==null || c.getLegal().iterator().next().getSiren()=="") continue;

            String siren = c.getLegal().iterator().next().getSiren();

            log.debug(">>>>>    TRY call Societe.com API with SIREN = "+siren +" <<<<<<");

            //Call API with SIREN
            Main newMain=null;
            try {
                ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.societe.com/pro/dev/societe/"+siren+"?token="+TOKEN,
                    HttpMethod.GET,
                    createRequest(),
                    String.class
                );

                log.debug(">>>>> API CALL CODE = " + response.getStatusCode() + " <<<<<");

                if (response.getStatusCode() != HttpStatus.OK) {
                    log.debug(">>> SIREN not found in Societe <<<");
                    continue;
                }
                if (response.getBody() == null){
                    log.debug(">>> Empty response body from Societe <<<");
                    continue;
                }


                String resStr = response.getBody().toString();
                JAXBContext context = JAXBContext.newInstance(Main.class);
                Unmarshaller um = context.createUnmarshaller();
                newMain = (Main)um.unmarshal(new ReaderInputStream(new StringReader(resStr)));

            } catch (Exception e) {
                //error
                log.debug("!!!!! >>>>>>>" + e.getMessage());
                continue;
            }

            SocieteMain societeMain = c.getSocieteMain().iterator().next();

            if(newMain==null){
                log.debug("!!!!! >>>>>>>   EMPTY RESPONSE FROM Societe.com for SIREN - " + siren + " <<<<<<<<<<<<" );
                continue;
            }

            societeMain.setAdresse(newMain.adresse);
            societeMain.setApe(newMain.ape);
            societeMain.setApetexte(newMain.apetexte);
            societeMain.setCapital(newMain.capital.value);
            societeMain.setCodepostal(newMain.codepostal);
            societeMain.setCommune(newMain.commune);
            societeMain.setDateimmat(newMain.dateimmat);
            societeMain.setDcren(newMain.dcren);
            societeMain.setDeno(newMain.deno);
            societeMain.setDevisecap(newMain.capital.devisecap);
            societeMain.setEnseigne(newMain.enseigne);
            societeMain.setFormejur(newMain.formejur);
            societeMain.setGreffe(newMain.greffe);
            societeMain.setNationalite(newMain.nationalite);
            societeMain.setNormcommune(newMain.normcommune);
            societeMain.setPsiret(newMain.psiret);
            societeMain.setSiren(newMain.no);
            societeMain.setTypecap(newMain.capital.typecap);
            societeMain.setUrl(newMain.url);

            //SAVE Fixed Societe Main
            societeMainRepository.save(societeMain);


            // DEVELOPMENT ONLY DELAY TODO
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }





}
