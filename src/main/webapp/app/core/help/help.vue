<template>
    <div class="help">
        <h1 class="help__title">{{ $t('help.title') }}</h1>
        <br>
        <div class="help__paragraph" v-if="lang === 'en'">
           <p>
               For this platform to behave properly it's very important to have the most full and correct info possible.<br> When entering any information, please make sure that the data is correct.<br>
               <b>Avoid putting in misleading numbers</b> such as Number of consultants for a company = 1.<br>
               When entering an address of a certain agency, make sure that the country, the city and the postal code are filled correctly, and the street address is complete.
           </p>
            <p>
                The information can be found in several sources:<br>
                <b>Financial and Legal</b> info usually comes from Infogreffe.fr and Societe.com. Most of this information is obtained automatically, but sometimes you can still find some useful data there.<br>
                The information regarding the number of offices, their locations, number of employees etc. can usually be found on the official web site of the competitor company or by a basic Google search.<br>
                <b>Technical</b> info, such as the used technologies, technical partners etc., can also usually be founds on the official web site.<br>
                Some data for the <b>HR</b> section can be obtained from the company's page on LinkedIn.com. There you can also find specific info regarding the employees, such as the name of the CTO.<br>
                A lot of the information in different sections (such as the details of the hiring process) can not be found in public resources, and can only be obtained from personal contacts.<br>
            </p>
            <p>
                If you need to enter the <b>URL</b> of a competitor for a certain social network (for example Twitter.com), just go to the network's web site, search for the page of the needed company and copy the URL from the top of the browser.<br>
                If the search gives you several results for the given company, make sure that you choose the official page of the company for the required country.
            </p>
            <br>
            <p><b>Thank you for making Kompetitors better!</b></p>
        </div>
        <div class="help__paragraph" v-if="lang === 'fr'">
            <p>
                Pour que cette plate-forme se comporte correctement, il est très important d'avoir les informations les plus complètes et les plus correctes possibles. <br> Lorsque vous entrez des informations, assurez-vous que les données sont correctes. <br>
                <b> Évitez de mettre des chiffres trompeurs </b> tels que Nombre de consultants pour une entreprise = 1. <br>
                Lorsque vous entrez l'adresse d'une certaine agence, assurez-vous que le pays, la ville et le code postal sont correctement renseignés et que l'adresse municipale est complète.
            </p>
            <p>
                Les informations peuvent être trouvées dans plusieurs sources: <br>
                Les informations <b> Financières et Juridiques </b> proviennent généralement d'Infogreffe.fr et de Societe.com. La plupart de ces informations sont obtenues automatiquement, mais parfois vous pouvez toujours y trouver des données utiles. <br>
                Les informations concernant le nombre de bureaux, leur emplacement, le nombre d'employés, etc. peuvent généralement être trouvées sur le site Web officiel de l'entreprise concurrente ou par une recherche de base sur Google. <br>
                Les informations <b> Techniques </b>, telles que les technologies utilisées, les partenaires techniques, etc., peuvent également être trouvées sur le site Web officiel. <br>
                Certaines données de la section <b> RH </b> peuvent être obtenues à partir de la page de l'entreprise sur LinkedIn.com. Vous y trouverez également des informations spécifiques concernant les employés, telles que le nom du CTO. <br>
                Beaucoup d'informations dans différentes sections (telles que les détails du processus d'embauche) ne peuvent pas être trouvées dans les ressources publiques et ne peuvent être obtenues qu'à partir de contacts personnels. <br>
            </p>
            <p>
                Si vous devez saisir l'<b>URL </b> d'un concurrent pour un certain réseau social (par exemple Twitter.com), allez simplement sur le site Web du réseau, recherchez la page de la société recherchée et copiez l'URL depuis le haut du navigateur. <br>
                Si la recherche vous donne plusieurs résultats pour l'entreprise donnée, assurez-vous de choisir la page officielle de l'entreprise pour le pays souhaité.
            </p>
            <br>
            <p> <b> Merci d'avoir amélioré les Kompetitors! </b> </p>
        </div>
    </div>
</template>
<script lang="ts" src="./help.component.ts">
</script>
<style scoped>
    .help {
        padding: 25px;
    }
    .help__title {
        font-size: 34px;
        font-weight: 700;
    }
    .help__paragraph {
        margin: 20px 0 0;
    }
</style>
