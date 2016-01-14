<?php

/* * *******************************************************
 * Partie SERVEUR du service web RESTful. 
 * ******************************************************* */


/**
 * Connexion au SGBD
 */
try {
    $pdo = new PDO('mysql:host=localhost;port=3306;dbname=bd_rest', 'root', '');
} catch (PDOException $e) {
    die("Erreur : " . $e->getMessage());
}


/**
 * Champs de la ressource ville
 */
$cols = array('Nom_Ville', 'MAJ', 'Code_Postal', 'Code_INSEE', 'Code_Region', 'Latitude', 'Longitude', 'Eloignement');

/**
 * Verbe HTTP
 */
$method = $_SERVER['REQUEST_METHOD'];

/**
 * Application du verbe sur la ressource
 */
switch ($method) {
    case 'GET' : {
        try{
            getVille();
        }catch(Exception $e){
            echo $e->getMessage();
        }
    } break;
    case 'DELETE' : {
        //delete_ville();
    } break;
    case 'PUT' : {
        //update_ville();
    } break;
    case 'POST' : {
        //create_ville();
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
 * Cherche une ville en fonction du paramètre id
 */
function getVille() {
    if (isset($_GET['id'])) {
        $id = $_GET['id'];
        
        global $pdo, $cols, $sql;
        
        /**
         * Requète SQL
         */
        //$sql = "SELECT * FROM `villes` LIMIT 5";		
        $sql = "SELECT * FROM `villes` WHERE Code_INSEE=$id";

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

?>