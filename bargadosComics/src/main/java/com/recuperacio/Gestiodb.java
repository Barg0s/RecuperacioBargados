package com.recuperacio;

public class Gestiodb {






    public static void crearAutor() {
        AppData db = AppData.getInstance();
        
        // Eliminar los autores existentes
        String deleteAutorSql = "DELETE FROM autor";
        db.update(deleteAutorSql);
        
        // Insertar nuevos autores de manga
        String sql = """
            INSERT INTO autor (nom, cognom, pais, any_naixement, foto) 
            VALUES
                ('Akira', 'Toriyama', 'Japón', 1955, 'assets/images/toriyama.jpg'),
                ('Tite', 'Kubo', 'Japón', 1977, 'assets/images/kubo.jpg'),
                ('Koyoharu', 'Gotouge', 'Japón', 1989, 'assets/images/croco.jpg'),
                ('Gege', 'Akutami', 'Japón', 1992, 'assets/images/gege.jpg'),
                ('Masashi', 'Kishimoto', 'Japón', 1974, 'assets/images/kishimoto.jpg'),
                ('Eiichiro', 'Oda', 'Japón', 1975, 'assets/images/oda.jpg'),
                ('Kohei', 'Horikoshi', 'Japón', 1986, 'assets/images/horikoshi.png'),
                ('Hajime', 'Isayama', 'Japón', 1986, 'assets/images/isayama.jpg'),
                ('Hiro', 'mashima', 'Japón', 1986, 'assets/images/mashima.png'),
                ('Hirohiko', 'Araki', 'Japón', 1977, 'assets/images/araki.jpeg'),
                ('Hiromu', 'Arakawa', 'Japón', 1973, 'assets/images/arawaka.jpg'),
                ('Yoshihiro', 'Togashi', 'Japón', 1966, 'assets/images/togashi.jpg');
        """;
        
        // Ejecutar la inserción de los autores
        db.update(sql);
    }
    
    public static void crearManga() {
        AppData db = AppData.getInstance();
    
        String deleteMangaSql = "DELETE FROM manga";
        db.update(deleteMangaSql);
    
        // Insertar nous mangas
        String sql = "INSERT INTO manga (titol, preu, id_autor, sinopsi, ISBN, data_publicacio, pags, portada) VALUES "
            + "('Dragon Ball 01', 12.30, 1, 'Son Goku és un noi molt especial que, després de la mort del seu avi, viu sol al bosc. Posseeix una força prodigiosa i sembla força satisfet amb la seva vida a l''aire lliure fins que coneix la Bulma, una nena rica que està buscant les Boles de Drac, set esferes màgiques que, un cop reunides, poden concedir qualsevol desig. Casalment, Goku posseeix una d''elles, la de quatre estrelles, de manera que a la Bulma li costa una mica convèncer-lo per a què s''uneixi a ella en la seva cerca. . . Així comença el manga més popular de tots els temps!', 9788413418490, '1984-11-01', 200, 'assets/images/db01.png'), "
            + "('Guardians de la nit 01', 9, 3, 'Tanjirou Kamado és un noi alegre i treballador que viu feliçment juntament amb la seva família, fins que un fatídic dia un dimoni anomenat Muzan irromp en la seva vida matant els seus pares i maleint la seva germana petita convertint-la en dimoni. Després d''aquest incident, i amb el propòsit de venjar els seus pares i retornar la seva germana Nezuko a la normalitat, decideix convertir-se en un assassí de dimonis. Per a això, cerca l''ajuda de Sakonji Urokodaki, un dels assassins de dimonis més forts del món...', 9788467960969, '2019-03-01', 192, 'assets/images/kny.jpg'), "
            + "('Bleach 01', 16.10, 2, 'Ichigo Kurosaki no és un adolescent normal... pot veure esperits i té un contacte innat amb l''altre món, del qual treu profit després de conèixer a un shinigami (àngel de la mort) que li proporciona una espasa a joc amb les seves habilitats.', 9788411507271, '2018-08-09', 384, 'assets/images/bleach01.png'), "
            + "('Naruto 01', 14.50, 5, 'Naruto Uzumaki és un jove ninja que aspira a convertir-se en el Hokage del seu poble i obtenir el respecte dels altres. Al llarg del seu viatge, s''enfronta a diferents reptes i descobreix secrets sobre el seu passat i el seu vincle amb el Dimoni de Nueve Cues.', 9788401333695, '1999-10-03', 200, 'assets/images/naruto01.jpg'), "
            + "('One Piece 01', 17.00, 6, 'Monkey D. Luffy és un noi amb el somni de convertir-se en el Rei dels Pirates. En el seu viatge pels mars, s''uneix a una tripulació de pirates i s''enfronta a nombrosos reptes en la seva recerca del llegendari tresor, el One Piece.', 9788467443124, '1997-07-22', 300, 'assets/images/onepiece01.jpg'), "
            + "('My Hero Academia 01', 16.00, 7, 'Izuku Midoriya és un noi que neix sense poders en un món on gairebé tots els humans posseeixen habilitats especials. Tot i això, somia amb convertir-se en un heroi i, després d''un encontre amb el seu ídol All Might, rep un poder especial.', 9788491671632, '2014-07-07', 220, 'assets/images/mha01.jpg'), "
            + "('Attack on Titan 01', 13.50, 8, 'En un món on la humanitat està al límit de l''extinció a causa dels titans, gegants que devoren humans, Eren Yeager i els seus amics s''uneixen a l''exèrcit per lluitar per la supervivència.', 9788498387001, '2009-09-09', 160, 'assets/images/aot01.jpg'), "
            + "('Fullmetal Alchemist 01', 18.00, 11, 'Edward i Alphonse Elric, dos germans que intenten reviure la seva mare mitjançant alquímia, sofreixen terribles conseqüències. Al llarg del seu viatge, busquen la Pedra Filosofal per restaurar els seus cossos.', 9788467455460, '2001-07-11', 250, 'assets/images/fma01.jpg'), "
            + "('Jujutsu Kaisen 23', 9.00, 4, 'En Sukuna ha revelat que l''objectiu de l''Àngel, l''individu que ella va anomenar ''el Caigut'', és ell mateix. L''Itadori i en Fushiguro no acaben de comprendre quina deu ser l''autèntica intenció d''en Sukuna en revelar-los aquesta informació i, abans que ho puguin esclarir, en Kenjaku desferma una nova onada de caos sobre el Joc de l''aniquilació... ja que ha estat conspirant contra diversos països! Per acabar-ho d''adobar, en Kenjaku arriba finalment fins al lloc on s''ha reclòs en Tengen... el Santuari dels Estels Morts!', 9788411508124, '2024-03-01', 192, 'assets/images/jujutsu-kaisen-23.jpg'), "
            + "('Hunter x Hunter 01', 16.00, 12, 'Gon Freecss és un noi que aspira a convertir-se en un caçador, un títol que permet accedir a una gran quantitat de recursos. Juntament amb els seus amics, emprèn una sèrie d''aventures perilloses.', 9788467450076, '1998-03-03', 310, 'assets/images/hxh01.jpg'), "
            + "('Fairy Tail 01', 9.50, 9, 'Natsu Dragneel és un jove mag i membre del gremi *Fairy Tail*, que viatja en cerca del drac Igneel, qui el va adoptar com a fill. En el seu camí, Natsu coneix a Lucy Heartfilia, una jove maga celestial que vol formar part del gremi *Fairy Tail*. Junts, s''embarquen en diverses aventures i desafiaments mentre lluiten per superar els seus propis límits.', 9788491463662, '2006-12-02', 200, 'assets/images/fairytail01.jpg'), "
            + "('Steel Ball Run 01', 13.00, 10, 'Steel Ball Run narra una cursa èpica a través dels Estats Units, organitzada per l''estrambòtic Gyro Zeppeli i el jove Johnny Joestar. Amb una combinació d''habilitats de batalla i esperit de competició, els participants lluiten per aconseguir el premi més gran. Johnny, un jove que ha perdut la capacitat de caminar, es veu embolicat en una trama de conspiracions i venjança mentre participa en la cursa.', 9788491742811, '2004-12-17', 250, 'assets/images/sbr01.jpg');";
    
        db.update(sql);
    }
    
    
    public static void insertarUsers() {
        AppData db = AppData.getInstance();
    
        String deleteClientsSql = "DELETE FROM usuaris";
        db.update(deleteClientsSql);
    
        String sql = """
            INSERT INTO usuaris (nom, cognom, password, rol, email, dni)
            VALUES 
            ('David', 'Bargados', 'dbargados', 'admin', 'dbargadosgomez.cf@iesesteveterradas.cat', '48078986Q'),
            ('a', 'a', 'a', 'admin', 'a@a.com', '12345678B'),
            ('Marc', 'Cachinero', 'mcachinero', 'client', 'mcachinerobaranda.cf@iesesteveterradas.cat', '12345678A');
            """;
        
    
        db.update(sql);
    }
    public static void insertarStock() {
        AppData db = AppData.getInstance();
        
        // Eliminar datos existentes en la tabla stock
        String deleteStockSql = "DELETE FROM stock";
        db.update(deleteStockSql);
        
        // Insertar datos predeterminados en la tabla stock
        String sql = """
            INSERT INTO stock (id_manga, quantitat, estat)
            VALUES 
            ('1', 100, 'En Stock'),
            ('2', 150, 'En Stock');
            """;
        
        db.update(sql);
    }
    
    public static void crearTaulaAutor() {
        AppData db = AppData.getInstance();
        db.connect("src\\main\\resources\\assets\\data\\editorial.sqlite");
        db.update("DROP TABLE IF EXISTS autor");
        db.update("""
                CREATE TABLE IF NOT EXISTS autor(
                id INTEGER PRIMARY KEY,
                nom VARCHAR(255) NOT NULL,
                cognom VARCHAR(255) NOT NULL,
                pais VARCHAR(100),
                any_naixement INT,
                foto TEXT
                )
                """);
    }

    public static void crearTaulaManga() {
        AppData db = AppData.getInstance();
        db.update("DROP TABLE IF EXISTS manga");
        db.update("""
                CREATE TABLE IF NOT EXISTS manga(
                id_manga INTEGER PRIMARY KEY,
                titol VARCHAR(255) NOT NULL UNIQUE,
                preu DECIMAL(10,2),
                id_autor INTEGER,
                sinopsi VARCHAR(255) NOT NULL,                
                ISBN INT NOT NULL,
                data_publicacio DATE,
                pags INT NOT NULL,
                portada TEXT,
                FOREIGN KEY (id_autor) REFERENCES autor(id) ON DELETE CASCADE
                )
                """);
    }

    public static void crearTaulaClients() {
        AppData db = AppData.getInstance();
        
        db.update("DROP TABLE IF EXISTS usuaris");
        db.update("""
                CREATE TABLE IF NOT EXISTS usuaris (
                    id_user INTEGER PRIMARY KEY,
                    nom VARCHAR(255) NOT NULL,
                    cognom VARCHAR(255) NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    rol VARCHAR(255) NOT NULL,
                    email VARCHAR(255) UNIQUE NOT NULL,
                    dni VARCHAR(20) UNIQUE NOT NULL 
                )
                """);
    }


    public static void InsertarCompras(){
        AppData db = AppData.getInstance();
        String deleteStockSql = "DELETE FROM registre_vendes";
        db.update(deleteStockSql);
        String sql2 = "INSERT INTO registre_vendes (id_user, id_manga, data_compra) " +
        "VALUES ('1', '1', CURRENT_TIMESTAMP);";            
        db.update(sql2);
    }
    public static void crearTaulaStock() {
        AppData db = AppData.getInstance();
        
        db.update("DROP TABLE IF EXISTS stock");
        db.update("""
                CREATE TABLE IF NOT EXISTS stock (
    id INTEGER PRIMARY KEY,
    id_manga INTEGER NOT NULL,
    quantitat INTEGER NOT NULL,
    estat VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_manga) REFERENCES manga(id_manga) ON DELETE CASCADE
)

                """);
    }
    

    public static void crearTaulaVendes() {
        AppData db = AppData.getInstance();
        db.update("DROP TABLE IF EXISTS registre_vendes");
        db.update("""
                CREATE TABLE IF NOT EXISTS registre_vendes(
                    id INTEGER PRIMARY KEY,
                    id_user INTEGER,
                    id_manga INTEGER,
                    data_compra DATE,
                    FOREIGN KEY (id_user) REFERENCES usuaris(id_user),
                    FOREIGN KEY (id_manga) REFERENCES manga(id_manga)
                )
                """);
    }
}
