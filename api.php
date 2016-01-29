<?php

/* * *******************************************************
 * Partie SERVEUR du service web RESTful. 
 * ******************************************************* */


/**
 * Connexion au SGBD
 */
try {
    $pdo = new PDO('mysql:host=localhost;port=3306;dbname=bd_rest', 'root', 'root');
} catch (PDOException $e) {
    die("Erreur : " . $e->getMessage());
}


/**
 * Champs de la ressource ville
 */
$cols = array('Nom_Ville', 'MAJ', 'Code_Postal', 'Code_INSEE', 'Code_Region', 'Latitude', 'Longitude', 'Eloignement');

/**
 * Array de correspondances de noms de colonnes
 */

$colonnesConverter = array(
    'nom' => 'Nom_Ville',
    'maj' => 'MAJ',
    'codepostal' => 'Code_Postal',
    'codeinsee' => 'Code_INSEE',
    'coderegion' => 'Code_Region'
);

/**
 * Verbe HTTP
 */
$method = $_SERVER['REQUEST_METHOD'];

/**
 * Application du verbe sur la ressource
 */
switch ($method) {
    case 'GET' : {
        if (isset($_GET['action']) && $_GET['action'] == "all") {
            try {
                getVilles();
            } catch (Exception $e) {
                echo $e->getMessage();
            }
        } else {
            try {
                getVille();
            } catch (Exception $e) {
                echo $e->getMessage();
            }
        }
    } break;
    case 'DELETE' : {
        //deleteVille();
    } break;
    case 'PUT' : {
        //updateVille();
    } break;
    case 'POST' : {
        if(isset($_POST['action'])) {
            if ($_POST['action'] == "delete") {
                deleteVille();
            } else if ($_POST['action'] == "create") {
                createVille();
            } else if ($_POST['action'] == "update") {
                updateVille();
            }
        }
    } break;
    default : {
        echo "méthode inconnue";
    }
}

/**
 * Exécute le verbe READ sur la ressource VILLES
 */
function getVilles() {
    global $pdo, $cols, $sql;
    /**
     * Requète SQL
     */
    //$sql = "SELECT * FROM `villes` LIMIT 5";		
    $sql = "SELECT * FROM `villes`";

    if ($stmt = $pdo->query($sql)) {
        $items = $stmt->fetchAll();
    }

    $newArray = array(
        "villes" => $items
    );

    /**
     * Affichage terminal : présentation en JSON
     */
    header('Content-Type: application/json');
    echo json_encode($newArray);
}

/**
 * Cherche une ville en fonction du paramètre q
 */
function getVille() {
    global $pdo, $colonnesConverter, $sql;
    
    if (isset($_GET['q'])) {
        $q = $_GET['q'];
        
        if(isset($_GET['filtre']) AND array_key_exists($_GET['filtre'], $colonnesConverter)){
            // On a bien un nom de colonne valqe on continue
            $colRech = $colonnesConverter[$_GET['filtre']];
            
            $sql = "SELECT * FROM `villes` WHERE $colRech LIKE '%$q%'";
        }else{
            $sql = "SELECT * FROM `villes` WHERE Code_INSEE=$q";
        }

        if ($stmt = $pdo->query($sql)) {
            $items = $stmt->fetchAll();
        }

        $newArray = array(
            "villes" => $items
        );

        /**
         * Affichage terminal : présentation en JSON
         */
        header('Content-Type: application/json');
        echo json_encode($newArray);
    } else {
        throw new \Exception('You must provide an argument for the city you are looking for.');
    }
}


/**
 * Supprime une ville à partir de son ID
 */
function deleteVille()
{
    global $pdo, $sql;

    if (isset($_POST['codeinsee'])) {
        $sql = "DELETE FROM villes WHERE Code_INSEE =  :Code_INSEE";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':Code_INSEE', $_POST['codeinsee'], PDO::PARAM_INT);
        $stmt->execute();
    }
}

/**
 * Update une ville à partir de son Code Insee et des données POST
 */
function updateVille()
{
    global $pdo;

    if (isset($_POST)) {
        $req = $pdo->prepare('UPDATE villes SET Nom_Ville = :nomville, MAJ = :maj, Code_Postal = :codepostal, Code_Region = :coderegion, Latitude = :latitude, Longitude = :longitude, Eloignement = :eloignement WHERE Code_Insee = :codeinsee');
        $req->execute(array(
            'nomville' => $_POST['nomville'],
            'maj' => $_POST['maj'],
            'codepostal' => $_POST['codepostal'],
            'coderegion' => $_POST['coderegion'],
            'latitude' => $_POST['latitude'],
            'longitude' => $_POST['longitude'],
            'eloignement' => $_POST['eloignement'],
            'codeinsee' => $_POST['codeinsee'],
        ));
    }
}

/**
 *  Crée une ville à partir de données POST
 */
function createVille()
{
    global $pdo;

    if (isset($_POST)) {
        $req = $pdo->prepare('INSERT INTO villes VALUES (Nom_Ville = :nomville,  MAJ = :maj, Code_Postal = :codepostal, Code_Insee = :codeinsee, Code_Region = :coderegion, Latitude = :latitude, Longitude = :longitude, Eloignement = :eloignement)');
        $req->execute(array(
            'nomville' => $_POST['nomville'],
            'maj' => $_POST['maj'],
            'codepostal' => $_POST['codepostal'],
            'codeinsee' => $_POST['codeinsee'],
            'coderegion' => $_POST['coderegion'],
            'latitude' => $_POST['latitude'],
            'longitude' => $_POST['longitude'],
            'eloignement' => $_POST['eloignement']
        ));
    }
}

?>