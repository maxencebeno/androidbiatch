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
    case 'GET' :
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
        break;
    case 'DELETE' :
        deleteVille();
        break;
    case 'PUT' :
        updateVille();
        break;
    case 'POST' :
        createVille();
        break;
    default :
        echo "méthode inconnue";

}

/**
 * Exécute le verbe READ sur la ressource VILLES
 */
function getVilles()
{
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
function getVille()
{
    global $pdo, $colonnesConverter, $sql;

    if (isset($_GET['q'])) {
        $q = $_GET['q'];

        if (isset($_GET['filtre']) AND array_key_exists($_GET['filtre'], $colonnesConverter)) {
            // On a bien un nom de colonne valqe on continue
            $colRech = $colonnesConverter[$_GET['filtre']];

            $sql = "SELECT * FROM `villes` WHERE $colRech LIKE '%$q%'";
        } else {
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

    parse_str(file_get_contents("php://input"),$post_vars);

    if (isset($post_vars['codeinsee'])) {
        $sql = "DELETE FROM villes WHERE Code_INSEE =  :Code_INSEE";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':Code_INSEE', $post_vars['codeinsee'], PDO::PARAM_INT);
        $stmt->execute();
    }
}

/**
 * Update une ville à partir de son Code Insee et des données POST
 */
function updateVille()
{
    global $pdo;

    parse_str(file_get_contents("php://input"),$post_vars);

    if (isset($post_vars)) {
        $req = $pdo->prepare('UPDATE villes SET Nom_Ville = :nomville, MAJ = :maj, Code_Postal = :codepostal, Code_Region = :coderegion, Latitude = :latitude, Longitude = :longitude, Eloignement = :eloignement WHERE Code_INSEE = :codeinsee');
        $req->execute(array(
            'nomville' => strtolower($post_vars['maj']),
            'maj' => $post_vars['maj'],
            'codepostal' => $post_vars['codepostal'],
            'coderegion' => $post_vars['coderegion'],
            'latitude' => $post_vars['latitude'],
            'longitude' => $post_vars['longitude'],
            'eloignement' => $post_vars['eloignement'],
            'codeinsee' => $post_vars['codeinsee'],
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
        $req = $pdo->prepare('INSERT INTO villes (Nom_Ville, MAJ, Code_Postal, Code_INSEE, Code_Region, Latitude, Longitude, Eloignement) VALUES (?, ?, ?, ?, ?, ?, ?, ?)');
        $req->execute(array(
            strtolower($_POST['maj']),
            $_POST['maj'],
            $_POST['codepostal'],
            $_POST['codeinsee'],
            $_POST['coderegion'],
            $_POST['latitude'],
            $_POST['longitude'],
            $_POST['eloignement']
        ));
    }
}

?>