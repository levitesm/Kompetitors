package fr.ippon.kompetitors.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.ippon.kompetitors.domain.*;
import fr.ippon.kompetitors.repository.*;
import fr.ippon.kompetitors.web.rest.CompetitorsResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class InfogreffeService {

    @Value("${infohreffe-call-enabled}")
    private Boolean infogreffe_call_enabled;

    private final RestTemplate restTemplate;
    private final CompetitorsRepository competitorsRepository;
    private final InfogreffeRepository infogreffeRepository;
    private final RepresentativesRepository representativesRepository;
    private final ShareHoldersRepository shareHoldersRepository;
    private final CapitalRepository capitalRepository;
    private final UpdatehistoryRepository updatehistoryRepository;

    // DEMO private final String TOKEN = "Y647xR88GGmnoJFgPPxXlV5eWYxtakonLkI61TmSQMl6FoSM1mMHZiOSlOsWk"; // 63XD4tGI5igGzXzcUxYkaA8Af0boCyqjRLrCdjgALO6lwzX31mDo0qTkZCwr4
    private final String TOKEN = "63XD4tGI5igGzXzcUxYkaA8Af0boCyqjRLrCdjgALO6lwzX31mDo0qTkZCwr4";

    private final Logger log = LoggerFactory.getLogger(CompetitorsResource.class);


    public InfogreffeService(RestTemplate restTemplate, CompetitorsRepository competitorsRepository, InfogreffeRepository infogreffeRepository, RepresentativesRepository representativesRepository, ShareHoldersRepository shareHoldersRepository, CapitalRepository capitalRepository, UpdatehistoryRepository updatehistoryRepository) {

        this.restTemplate = restTemplate;
        this.competitorsRepository = competitorsRepository;
        this.infogreffeRepository = infogreffeRepository;
        this.representativesRepository = representativesRepository;
        this.shareHoldersRepository = shareHoldersRepository;
        this.capitalRepository = capitalRepository;
        this.updatehistoryRepository = updatehistoryRepository;
    }


    private HttpEntity<MultiValueMap<String, String>> createRequest() {
        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new HttpEntity<>(requestMap, headers);
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Response {
        @JsonProperty("records")
        private List<Record> records = new ArrayList<>();

        public List<Record> getRecords() {
            return records;
        }

        public void setRecords(List<Record> records) {
            this.records = records;
        }

        @Override
        public String toString() {
            return "Response{" +
                "records=" + records +
                '}';
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Record {
        @JsonProperty("fields")
        public Fields fields;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Fields {

        @JsonProperty("departement")
        public String departement;

        @JsonProperty("ville")
        public String ville;

        @JsonProperty("num_dept")
        public String numDept;

        @JsonProperty("code_greffe")
        public String codeGreffe;

        @JsonProperty("date_immatriculation")
        public String dateImmatriculation;

        @JsonProperty("ca_1")
        public String ca1;

        @JsonProperty("siren")
        public String siren;

        @JsonProperty("ca_2")
        public String ca2;

        @JsonProperty("forme_juridique")
        public String formeJuridique;

        @JsonProperty("resultat_3")
        public String resultat3;

        @JsonProperty("resultat_2")
        public String resultat2;

        @JsonProperty("resultat_1")
        public String resultat1;

        @JsonProperty("fiche_identite")
        public String ficheIdentite;

        @JsonProperty("duree_1")
        public String duree1;

        @JsonProperty("date_de_publication")
        public String dateDePublication;

        @JsonProperty("statut")
        public String statut;

        @JsonProperty("nic")
        public String nic;

        @JsonProperty("code_ape")
        public String codeApe;

        @JsonProperty("adresse")
        public String adresse;

        @JsonProperty("tranche_ca_millesime_3")
        public String trancheCaMillesime3;

        @JsonProperty("denomination")
        public String denomination;

        @JsonProperty("duree_2")
        public String duree2;

        @JsonProperty("effectif_1")
        public String effectif1;

        @JsonProperty("effectif_3")
        public String effectif3;

        @JsonProperty("effectif_2")
        public String effectif2;

        @JsonProperty("ca_3")
        public String ca3;

        @JsonProperty("tranche_ca_millesime_1")
        public String trancheCaMillesime1;

        @JsonProperty("duree_3")
        public String duree3;

        @JsonProperty("tranche_ca_millesime_2")
        public String trancheCaMillesime2;

        @JsonProperty("code_postal")
        public String codePostal;

        @JsonProperty("date_de_cloture_exercice_1")
        public String dateDeClotureExercice1;

        @JsonProperty("date_de_cloture_exercice_3")
        public String dateDeClotureExercice3;

        @JsonProperty("date_de_cloture_exercice_2")
        public String dateDeClotureExercice2;

        @JsonProperty("libelle_ape")
        public String libelleApe;

        @JsonProperty("greffe")
        public String greffe;

        @JsonProperty("millesime_3")
        public String millesime3;

        @JsonProperty("millesime_2")
        public String millesime2;

        @JsonProperty("millesime_1")
        public String millesime1;

        @JsonProperty("region")
        public String region;

        @Override
        public String toString() {
            return "Record{" +
                "departement='" + departement + '\'' +
                ", ville='" + ville + '\'' +
                ", numDept='" + numDept + '\'' +
                ", codeGreffe='" + codeGreffe + '\'' +
                ", dateImmatriculation='" + dateImmatriculation + '\'' +
                ", ca1='" + ca1 + '\'' +
                ", siren='" + siren + '\'' +
                ", ca2='" + ca2 + '\'' +
                ", formeJuridique='" + formeJuridique + '\'' +
                ", resultat3='" + resultat3 + '\'' +
                ", resultat2='" + resultat2 + '\'' +
                ", resultat1='" + resultat1 + '\'' +
                ", ficheidentite='" + ficheIdentite + '\'' +
                ", duree1='" + duree1 + '\'' +
                ", dateDePublication='" + dateDePublication + '\'' +
                ", statut='" + statut + '\'' +
                ", nic='" + nic + '\'' +
                ", codeApe='" + codeApe + '\'' +
                ", adresse='" + adresse + '\'' +
                ", trancheCaMillesime3='" + trancheCaMillesime3 + '\'' +
                ", denomination='" + denomination + '\'' +
                ", duree2='" + duree2 + '\'' +
                ", effectif1='" + effectif1 + '\'' +
                ", effectif3='" + effectif3 + '\'' +
                ", effectif2='" + effectif2 + '\'' +
                ", ca3='" + ca3 + '\'' +
                ", trancheCaMillesime1='" + trancheCaMillesime1 + '\'' +
                ", duree3='" + duree3 + '\'' +
                ", trancheCaMillesime2='" + trancheCaMillesime2 + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", dateDeClotureExercice1='" + dateDeClotureExercice1 + '\'' +
                ", dateDeClotureExercice3='" + dateDeClotureExercice3 + '\'' +
                ", dateDeClotureExercice2='" + dateDeClotureExercice2 + '\'' +
                ", libelleApe='" + libelleApe + '\'' +
                ", greffe='" + greffe + '\'' +
                ", millesime3='" + millesime3 + '\'' +
                ", millesime2='" + millesime2 + '\'' +
                ", millesime1='" + millesime1 + '\'' +
                ", region='" + region + '\'' +
                '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ResponseShares {

        public DataShares1 Data = new DataShares1();

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class DataShares1 {

        public DataShares Data = new DataShares();

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class DataShares {

        public CapitalSocial CapitalSocial = new CapitalSocial();

        public List<CapitalDetention> CapitalDetention = new ArrayList<>();

    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Identification {

        public String TypePersonne;

        public String Denomination;

        public String Civilite;

        public String NomPatronymique;

        public String NomUsage;

        public String Prenom;

        public String LibelleFormeJuridique;

        public String CodeFormeJuridique;

        public String Siren;

        public String DateNaissance;

    }


        @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CapitalDetention {

        public Identification Identification;

        public Long NbrParts;

        public Double PourcentageDetention;

         }


    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CapitalSocial {

        public String Montant;

        public String Devise;

        public Long NbrParts;

        public Double PourcentageDetentionPP;

        public Double PourcentageDetentionPM;

    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ResponseReps {
        @JsonProperty("Data")
        private DataReps data = new DataReps();

        public DataReps getData() {
            return data;
        }

        public void setData(DataReps data) {
            this.data = data;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class DataReps {
        @JsonProperty("Representants")
        private List<Representant> representants = new ArrayList<>();

        public List<Representant> getRepresentants() {
            return representants;
        }

        public void setRepresentants(List<Representant> representants) {
            this.representants = representants;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Representant {

        @JsonProperty("Qualite")
        public String qualite;

        @JsonProperty("Type")
        public String type;

        @JsonProperty("Nom")
        public String nom;

        @JsonProperty("Prenom")
        public String prenom;

        @JsonProperty("NomUsage")
        public String nomUsage;

        @JsonProperty("DateNaissance")
        public String dateNaissance;

        @JsonProperty("DenominationPM")
        public String denominationPM;

        @JsonProperty("SirenPM")
        public String sirenPM;

        public String getQualite() {
            return qualite;
        }

        public void setQualite(String qualite) {
            this.qualite = qualite;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public String getNomUsage() {
            return nomUsage;
        }

        public void setNomUsage(String nomUsage) {
            this.nomUsage = nomUsage;
        }

        public String getDateNaissance() {
            return dateNaissance;
        }

        public void setDateNaissance(String dateNaissance) {
            this.dateNaissance = dateNaissance;
        }

        public String getDenominationPM() {
            return denominationPM;
        }

        public void setDenominationPM(String denominationPM) {
            this.denominationPM = denominationPM;
        }

        public String getSirenPM() {
            return sirenPM;
        }

        public void setSirenPM(String sirenPM) {
            this.sirenPM = sirenPM;
        }
    }

    @Scheduled(fixedRate = 28800000) //Every 8 Hours
    public void updateInfogreffeInfo() {
        log.debug("X @#@ X @#@ X @#@ X @#@ X @#@ X ---- INFOGREFFE CALL ENABLED: " + infogreffe_call_enabled);
        if (!infogreffe_call_enabled) return;

        log.debug(" ");
        log.debug(" ");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug(" ");
        log.debug("                           ENTERING INFOGREFFE UPDATE");
        log.debug(" ");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        List<Competitors> competitors = competitorsRepository.findAll();

        for (Competitors c : competitors) {
            if (c.getLegal() == null) continue;
            if (c.getLegal().iterator().next().getSiren() == null || c.getLegal().iterator().next().getSiren() == "") continue;

            String siren = c.getLegal().iterator().next().getSiren();

            log.debug(">>>>>    TRY call INFOGREFFE API with SIREN = " + siren + " <<<<<<");

            //Call API with SIREN
            Response res = null;
            Infogreffe infgrf = new Infogreffe();
            Integer year = Calendar.getInstance().get(Calendar.YEAR);
            boolean found = false;
            while (year > 2010 && !found) {
                try {
//                Properties props = System.getProperties();
//                props.put("https.proxyHost", "103.89.253.246");
//                props.put("https.proxyPort", "3128");

                    ResponseEntity<Response> response = restTemplate.exchange(
                        "https://opendata.datainfogreffe.fr/api/records/1.0/search/?dataset=chiffres-cles-" + year.toString() + "&q=" + siren,
                        HttpMethod.GET,
                        createRequest(),
                        Response.class
                    );

                    log.debug(">>>>> API CALL CODE = " + response.getStatusCode() + " <<<<<");

                    if (response.getStatusCode() != HttpStatus.OK) {
                        log.debug(">>> SIREN not found in INFOGREFFE <<<");
                        continue;
                    }
                    if (response.getBody() == null) {
                        log.debug(">>> Empty response body from INFOGREFFE <<<");
                        continue;
                    }


                    res = response.getBody();
                    log.debug("GOT the Response  >>>>  " + res.toString() + "  <<<<");

                } catch (Exception e) {
                    //error
                    log.debug("!!!!! ERROR >>>>>>>" + e.getMessage());
                    continue;
                }

                if (c.getInfogreffe() != null && c.getInfogreffe().iterator().hasNext()) {
                    infgrf = c.getInfogreffe().iterator().next();
                } else {
                    infgrf.setCompetitor(c);
                }


                if (res == null) {
                    log.debug("!!!!! >>>>>>>   EMPTY RESPONSE FROM INFOGREFFE for SIREN - " + siren + " <<<<<<<<<<<<");
                    continue;
                }
                if (res.getRecords().size() == 0) {
                    log.debug("!!!!! >>>>>>>   NO RECORDS FROM INFOGREFFE for SIREN - " + siren + " for YEAR " + year.toString() + " <<<<<<<<<<<<");
                    year--;
                } else {
                    found=true;
                }
            }


            Fields r = res.getRecords().get(0).fields;
            log.debug(">>>>>>>   GOT GOOD RECORD (for SIREN " + siren + " for YEAR " + year.toString() + ") - " + r.toString() + " <<<<<<<<<<<<");

            infgrf.setAdresse(r.adresse);
            infgrf.setCa1(r.ca1);
            infgrf.setCa2(r.ca2);
            infgrf.setCa3(r.ca3);
            infgrf.setCodeApe(r.codeApe);
            infgrf.setCodeGreffe(r.codeGreffe);
            infgrf.setCodePostal(r.codePostal);
            infgrf.setDateDeClotureExercice1(r.dateDeClotureExercice1);
            infgrf.setDateDeClotureExercice2(r.dateDeClotureExercice2);
            infgrf.setDateDeClotureExercice3(r.dateDeClotureExercice3);
            infgrf.setDateDePublication(r.dateDePublication);
            infgrf.setDateImmatriculation(r.dateImmatriculation);
            infgrf.setDenomination(r.denomination);
            infgrf.setDepartement(r.departement);
            infgrf.setDuree1(r.duree1);
            infgrf.setDuree2(r.duree2);
            infgrf.setDuree3(r.duree3);
            infgrf.setEffectif1(r.effectif1);
            infgrf.setEffectif2(r.effectif2);
            infgrf.setEffectif3(r.effectif3);
            infgrf.setFicheidentite(r.ficheIdentite);
            infgrf.setFormeJuridique(r.formeJuridique);
            infgrf.setGreffe(r.greffe);
            infgrf.setLibelleApe(r.libelleApe);
            infgrf.setMillesime1(r.millesime1);
            infgrf.setMillesime2(r.millesime2);
            infgrf.setMillesime3(r.millesime3);
            infgrf.setNic(r.nic);
            infgrf.setNumDept(r.numDept);
            infgrf.setRegion(r.region);
            infgrf.setResultat1(r.resultat1);
            infgrf.setResultat2(r.resultat2);
            infgrf.setResultat3(r.resultat3);
            infgrf.setSiren(r.siren);
            infgrf.setStatut(r.statut);
            infgrf.setTrancheCaMillesime1(r.trancheCaMillesime1);
            infgrf.setTrancheCaMillesime2(r.trancheCaMillesime2);
            infgrf.setTrancheCaMillesime3(r.trancheCaMillesime3);
            infgrf.setVille(r.ville);

            //SAVE Fixed Infogreffe Main
            infogreffeRepository.save(infgrf);


            // DEVELOPMENT ONLY DELAY TODO
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


    public void updateRepresentatives(String siren) {
        log.debug(" ");
        log.debug(" ");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug(" ");
        log.debug("                           ENTERING INFOGREFFE - Representants For SIREN - " + siren);
        log.debug(" ");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        List<String> sirenList = new ArrayList<String>();
        sirenList.add(siren);
        List<Representatives> currentList = representativesRepository.findAllByCompetitorSiren(sirenList);

        ResponseReps res = null;
        try {

            String apiUrl = "https://api.datainfogreffe.fr/api/v1//Entreprise/Representants/" + siren + "?token=" + TOKEN;

            log.debug(">>>>> TRY URL - " + apiUrl);

            ResponseEntity<ResponseReps> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                createRequest(),
                ResponseReps.class
            );

            log.debug(">>>>> API CALL CODE = " + response.getStatusCode() + " <<<<<");

            if (response.getStatusCode() != HttpStatus.OK) {
                log.debug(">>> SIREN not found in INFOGREFFE <<<");
                return;
            }
            if (response.getBody() == null) {
                log.debug(">>> Empty response body from INFOGREFFE <<<");
                return;
            }


            res = response.getBody();
            log.debug("GOT the Response  >>>>  " + res.toString() + "  <<<<");

        } catch (Exception e) {
            //error
            log.debug("!!!!! ERROR >>>>>>>" + e.getMessage());
            return;
        }
        if (res == null) {
            log.debug("!!!!! >>>>>>>   EMPTY RESPONSE FROM INFOGREFFE-Representants for SIREN - " + siren + " <<<<<<<<<<<<");
            return;
        }
        if (res.getData().getRepresentants().size() == 0) {
            log.debug("!!!!! >>>>>>>   NO RECORDS FROM INFOGREFFE-Representants for SIREN - " + siren + " <<<<<<<<<<<<");
            return;
        }

        List<Representant> newList = res.getData().getRepresentants();

        for (Representatives rc : currentList) {
            if (!rc.isOld()) {
                boolean found = false;
                for (Representant rn : newList) {
                    if (compareReps(rc, rn)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    rc.setOld(true);
                    representativesRepository.save(rc); // Someone has left the Company
                }
            }
        }

        for (Representant rn : newList) {
            boolean found = false;
            for (Representatives rc : currentList) {
                if (compareReps(rc, rn)) {
                    found = true;
                    if (rc.isOld()) {
                        rc.setOld(false);
                        representativesRepository.save(rc); // Someone has returned to the Company
                    }
                    break;
                }
            }
            if (!found) {
                Representatives nrc = new Representatives();
                nrc.setCompetitorSiren(siren);
                nrc.setLinkedInUrl("");
                copyReps(nrc, rn);
                representativesRepository.save(nrc); // Someone New has joined the Company
            }
        }
        // ! XXX !
        for (Representant rn : newList) {
            if (rn.getType().equals("PM")) {
                Updatehistory urh = new Updatehistory();
                urh.setType("REPS");
                urh.setSiren(rn.sirenPM);
                urh.setDate(LocalDate.now());
                this.updatehistoryRepository.save(urh);
                updateRepresentatives(rn.sirenPM);
            }
        }
    }

    public void updateShareHoldersTest(String siren) {
        log.debug(" ");
        log.debug(" ");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug(" ");
        log.debug("                           !!! AAAAAAAAAAAAAAAAA !!! - ShareHolders For SIREN - " + siren);
        log.debug(" ");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        String str = "{\"Data\":{\"PdfUrl\":\"https://api.datainfogreffe.fr/pdf/ac2334591b0041a9a1a9fe0755aeff3d-350142071\",\"Data\":{\"ReferentielGreffe\":{\"CodeGreffe\":\"9201\",\"NomGreffe\":\"NANTERRE\",\"Adresse\":{\"AdresseConcat\":\"4 RUE PABLO NERUDA \",\"AdresseComplement\":\"\",\"CodePostal\":\"92020\",\"Commune\":\"NANTERRE\",\"Cedex\":\"NANTERRE CEDEX \",\"Pays\":\"France\",\"AdresseSuite\":\"\"}},\"SocieteInfos\":{\"Denomination\":\"SFEIR\",\"Siren\":\"350142071\",\"Registre\":\"R.C.S.\",\"LibelleGreffe\":\"NANTERRE\",\"Adresse\":{\"AdresseConcat\":\"48 RUE JACQUES DULUD\",\"AdresseComplement\":\"\",\"CodePostal\":\"92200\",\"Commune\":\"NEUILLY SUR SEINE\",\"Cedex\":null,\"Pays\":\"null\",\"AdresseSuite\":null},\"LibelleFormeJuridique\":\"Société par actions simplifiée à associé unique\",\"CodeFormeJuridique\":\"SASUh\"},\"CapitalSocial\":{\"Montant\":2215200.00,\"Devise\":\"EUR\",\"NbrParts\":22152,\"PourcentageDetentionPP\":0.00,\"PourcentageDetentionPM\":100.00},\"CapitalDetention\":[{\"Identification\":{\"TypePersonne\":\"Personne Morale\",\"Denomination\":\"DNA525\",\"Civilite\":null,\"NomPatronymique\":null,\"NomUsage\":null,\"Prenom\":null,\"LibelleFormeJuridique\":\"Société par actions simplifiée\",\"CodeFormeJuridique\":\"SASh\",\"Siren\":\"828607044\",\"Adresse\":{\"AdresseConcat\":\"48 RUE JACQUES DULUD\",\"AdresseComplement\":\"\",\"CodePostal\":\"92200\",\"Commune\":\"NEUILLY-SUR-SEINE\",\"Cedex\":null,\"Pays\":\"FRANCE\",\"AdresseSuite\":null},\"DateNaissance\":null,\"DateNaissanceISO\":null,\"VilleNaissance\":null,\"CodeInseeNaissance\":null},\"NbrParts\":22152,\"PourcentageDetention\":100.00}],\"RepresentantsDetail\":[{\"Identification\":{\"TypePersonne\":\"Personne Morale\",\"Denomination\":\"DNA525 - SOCIETE PAR ACTIONS SIMPLIFIEE A ASSOCIE UNIQUE\",\"Civilite\":null,\"NomPatronymique\":null,\"NomUsage\":null,\"Prenom\":null,\"LibelleFormeJuridique\":null,\"CodeFormeJuridique\":null,\"Siren\":\"828607044\",\"Adresse\":{\"AdresseConcat\":\"48 RUE JACQUES DULUD \",\"AdresseComplement\":\"48 RUE JACQUES DULUD\",\"CodePostal\":\"92200\",\"Commune\":\"NEUILLY-SUR-SEINE\",\"Cedex\":null,\"Pays\":\"FRANCE\",\"AdresseSuite\":null},\"DateNaissance\":null,\"DateNaissanceISO\":null,\"VilleNaissance\":null,\"CodeInseeNaissance\":null},\"Qualite\":\"Président\",\"CodeQualite\":\"PREH\"}],\"Depot\":{\"Date\":\"2017-06-08\",\"DateISO\":\"2017-06-08\",\"Numero\":\"20369\",\"Acte\":{\"Date\":\"2017-04-26\",\"DateISO\":\"2017-04-26\",\"Numero\":\"2\"}}}}}";
        try {
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ResponseShares response = om.readValue(str, ResponseShares.class);
            log.debug(response.toString());

            log.debug(response.Data.toString());
            log.debug(response.Data.Data.toString());
            log.debug(response.Data.Data.CapitalSocial.toString());
            log.debug(response.Data.Data.CapitalSocial.Montant);
            log.debug(response.Data.Data.CapitalDetention.get(0).Identification.Siren);
        }catch (Exception e) {log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> ERROR !!!");log.debug(e.getMessage());}


    }

    public void updateShareHolders(String siren) {
        log.debug(" ");
        log.debug(" ");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug(" ");
        log.debug("                           ENTERING INFOGREFFE - ShareHolders For SIREN - " + siren);
        log.debug(" ");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        List<String> sirenList = new ArrayList<String>();
        sirenList.add(siren);
        List<ShareHolders> currentList = shareHoldersRepository.findAllByCompetitorSiren(sirenList);
        List<Capital> oldCaps = capitalRepository.findAllByCompetitorSiren(sirenList);

        ResponseShares res = null;
        try {

            String apiUrl = "https://api.datainfogreffe.fr/api/v1//Entreprise/RepartitionCapital?siren=" + siren + "&restitution=pdf&token=" + TOKEN;

            log.debug(">>>>> TRY URL - " + apiUrl);

            ResponseEntity<ResponseShares> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                createRequest(),
                ResponseShares.class
            );

            log.debug(">>>>> API CALL CODE = " + response.getStatusCode() + " <<<<<");

            if (response.getStatusCode() != HttpStatus.OK) {
                log.debug(">>> SIREN not found in INFOGREFFE <<<");
                return;
            }
            if (response.getBody() == null) {
                log.debug(">>> Empty response body from INFOGREFFE <<<");
                return;
            }


            res = response.getBody();
            log.debug("GOT the Response  >>>>  " + res.toString() + "  <<<<");

        } catch (Exception e) {
            //error
            log.debug("!!!!! ERROR >>>>>>>" + e.getMessage());
            return;
        }
        if (res == null) {
            log.debug("!!!!! >>>>>>>   EMPTY RESPONSE FROM INFOGREFFE-ShareHolders for SIREN - " + siren + " <<<<<<<<<<<<");
            return;
        }
        if (res.Data.Data.CapitalDetention.size() == 0) {
            log.debug("!!!!! >>>>>>>   NO RECORDS FROM INFOGREFFE-ShareHolders for SIREN - " + siren + " <<<<<<<<<<<<");
            return;
        }
        log.debug(res.Data.Data.CapitalSocial.toString());
        log.debug(res.Data.Data.CapitalDetention.toString());
        log.debug("!>>" + Integer.toString(res.Data.Data.CapitalDetention.size()));

        // Capital
        Capital cap;

        if (oldCaps.size()==0){
            cap = new Capital();
            cap.setOld(false);
            cap.competitorSiren(siren);
            cap.setListed(false);
            cap.setPrivateCapital(false);
            cap.setIndependentC(false);
            cap.setIndependentE(false);
        } else {
            cap = oldCaps.get(0);
        }

        CapitalSocial newCap = res.Data.Data.CapitalSocial;

        cap.setMontant(Double.parseDouble(newCap.Montant));
        cap.setDevise(newCap.Devise);
        cap.setNbrParts(newCap.NbrParts);
        cap.setPourcentageDetentionPP(newCap.PourcentageDetentionPP);
        cap.setPourcentageDetentionPM(newCap.PourcentageDetentionPM);

        capitalRepository.save(cap);

        oldCaps = capitalRepository.findAllByCompetitorSiren(sirenList);
        cap = oldCaps.get(0);

        // ShareHolders

        List<CapitalDetention> newList = res.Data.Data.CapitalDetention;

        for (ShareHolders ss : currentList) {

            ss.setOld(true);
            shareHoldersRepository.save(ss); // Make the Old - OLD
        }

        Boolean reSave = false;
        for (CapitalDetention cd : newList) {
            ShareHolders newOne = new ShareHolders();
            newOne.setOld(false);
            newOne.setCompetitorSiren(siren);

            newOne.setTypePersonne(cd.Identification.TypePersonne);
            newOne.setDenomination(cd.Identification.Denomination);
            newOne.setCivilite(cd.Identification.Civilite);
            newOne.setNomPatronymique(cd.Identification.NomPatronymique);
            newOne.setNomUsage(cd.Identification.NomUsage);
            newOne.setPrenom(cd.Identification.Prenom);
            newOne.setLibelleFormeJuridique(cd.Identification.LibelleFormeJuridique);
            newOne.setCodeFormeJuridique(cd.Identification.CodeFormeJuridique);
            newOne.setSiren(cd.Identification.Siren);
            newOne.setDateNaissance(cd.Identification.DateNaissance);

            newOne.setNbrParts(cd.NbrParts);
            newOne.setPourcentageDetention(cd.PourcentageDetention);

            // Get APE Code
            if (newOne.getSiren() != null && !newOne.getSiren().equals("")){
                log.debug("MAKE CALL TO GET >> APE CODE << for Siren - " + newOne.getSiren());
                ResponseEntity<Response> response = restTemplate.exchange(
                    "https://opendata.datainfogreffe.fr/api/records/1.0/search/?dataset=chiffres-cles-2018&q=" + newOne.getSiren(),
                    HttpMethod.GET,
                    createRequest(),
                    Response.class
                );
                if (response != null && response.getStatusCode() == HttpStatus.OK && response.getBody().getRecords() != null && response.getBody().getRecords().size() > 0 && response.getBody().getRecords().get(0).fields != null) {
                    newOne.setCodeApe(response.getBody().getRecords().get(0).fields.codeApe);
                } else {
                    newOne.setCodeApe("APE Unknown");
                    log.debug("XXXXXXXX >>>>>>>>   NO APE for SIREN of the Shareholder Company - " + newOne.getSiren());
                }
            } else {
                if ("Personne Morale".equals(newOne.getTypePersonne())){
                    newOne.setCodeApe("APE Unknown");
                    log.debug("XXXXXXXX >>>>>>>>   NO SIREN FOR Shareholder Company");
                }
            }

            shareHoldersRepository.save(newOne);

            // SET Checkboxes
            if ("6430Z".equals(newOne.getCodeApe()) || "6630Z".equals(newOne.getCodeApe())) {
                cap.setPrivateCapital(true);
                reSave = true;
            }
            if (newOne.getPourcentageDetention()>50 && "Personne Physique".equals(newOne.getTypePersonne())) {
                cap.setIndependentC(true);
                reSave = true;
            }

        }
        if (reSave) {
            capitalRepository.save(cap);// Resave Capital with Checkboxes
        }
    }

    private boolean compareReps(Representatives r1, Representant r2) {
        return (r1.getNom() == r2.getNom() &&
            r1.getPrenom() == r2.getPrenom() &&
            r1.getNomUsage() == r2.getNomUsage() &&
            r1.getSirenPM() == r2.getSirenPM()
        );
    }

    private void copyReps(Representatives r1, Representant r2) {
        r1.setQualite(r2.getQualite());
        r1.setNom(r2.getNom());
        r1.setPrenom(r2.getPrenom());
        r1.setNomUsage(r2.getNomUsage());
        r1.setDateNaissance(r2.getDateNaissance());
        r1.setSirenPM(r2.getSirenPM());
        r1.setDenominationPM(r2.getDenominationPM());
        r1.setType(r2.getType());
        r1.setOld(false);
    }
}
