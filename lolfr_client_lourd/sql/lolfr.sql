CREATE DATABASE lolfr;
USE lolfr;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


CREATE TABLE `admin` (
  `ID_Admin` int(11) NOT NULL,
  `Prenom` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `MotDePasse` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



INSERT INTO `admin` (`ID_Admin`, `Prenom`, `Email`, `MotDePasse`) VALUES
(1, 'Enzo', 'enzo@gmail.com', '123');


ALTER TABLE `admin`
  ADD PRIMARY KEY (`ID_Admin`);


ALTER TABLE `admin`
  MODIFY `ID_Admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;


CREATE TABLE coach(
   id_coach INT,
   pseudo VARCHAR(50),
   prenom VARCHAR(50),
   nom VARCHAR(50),
   PRIMARY KEY(id_coach)
);



CREATE TABLE compte(
   id_compte INT AUTO_INCREMENT,
   pseudo VARCHAR(50),
   prenom VARCHAR(50),
   mdp VARCHAR(255),
   nom VARCHAR(50),
   date_naissance DATE,
   nationalite VARCHAR(50),
   adresse VARCHAR(50),
   email VARCHAR(50),
   PRIMARY KEY(id_compte)
);


CREATE TABLE role(
   id_role INT,
   libelle VARCHAR(50),
   PRIMARY KEY(id_role)
);




CREATE TABLE ligue(
   id_ligue INT,
   nom VARCHAR(50),
   region VARCHAR(50),
   PRIMARY KEY(id_ligue)
);



CREATE TABLE equipe(
   id_equipe INT,
   nom VARCHAR(50),
   champion BOOLEAN,
   classement INT,
   victoire INT,
   defaite INT,
   id_ligue INT NOT NULL,
   id_coach INT NOT NULL,
   logo LONGBLOB NOT NULL,
   PRIMARY KEY(id_equipe),
   UNIQUE(id_coach),
   FOREIGN KEY(id_ligue) REFERENCES ligue(id_ligue),
   FOREIGN KEY(id_coach) REFERENCES coach(id_coach)
);



CREATE TABLE joueur(
   id_joueur INT,
   pseudo VARCHAR(50),
   prenom VARCHAR(50),
   nom VARCHAR(50),
   date_naissance DATE,
   nationalite VARCHAR(50),
   date_rejoindre DATE,
   id_role INT NOT NULL,
   id_equipe INT,
   PRIMARY KEY(id_joueur),
   FOREIGN KEY(id_role) REFERENCES role(id_role),
   FOREIGN KEY(id_equipe) REFERENCES equipe(id_equipe)
);






CREATE TABLE suivre(
   id_equipe INT,
   id_compte INT,
   PRIMARY KEY(id_equipe, id_compte),
   FOREIGN KEY(id_equipe) REFERENCES equipe(id_equipe),
   FOREIGN KEY(id_compte) REFERENCES compte(id_compte)
);

CREATE TABLE affronter(
   id_equipe INT,
   id_equipe_1 INT,
   resultat VARCHAR(50),
   PRIMARY KEY(id_equipe, id_equipe_1),
   FOREIGN KEY(id_equipe) REFERENCES equipe(id_equipe),
   FOREIGN KEY(id_equipe_1) REFERENCES equipe(id_equipe)
);
