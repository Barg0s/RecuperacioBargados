# **Recuperar les UFs pendents de programació**

Aquestes són les condicions:

- Ha de tenir almenys 5 taules relacionades entre elles
- Ha de tenir almenys 5 classes modelant les taules anteriors, fent ús d'herencia
- Els atributs dels objeces .java que modelen les taules anteriors han de fer ús de setters i getters
- Cada modificació a l’estructura anterior, o crida als ’setters’ ha de guardar la informació a la base de dades en temps real
- Cada lectura de dades ha de validar la informació des de la base de dades
- Ha de poder fer gestió de l’estructura i els objectes (afegir, modificar, esborrar elements de cada un dels tipus d’elements)
- Ha de ser una aplicació gràfica

### TAULES

- Manga
- | id_manga | Titol | id/Nom_autor | any_publicacio | id_autor | preu | portadaº |
  | -------- | ----- | ------------ | -------------- | -------- | ---- | --------- |
  |          |       |              |                |          |      |           |
- "Registre" de ventas
- | id | id_user | id_manga | data_compra |
  | -- | ------- | -------- | ----------- |
  |    |         |          |             |
  |    |         |          |             |
- Clients ?
- | id_user | nom | cognom | email | foto | dni |
  | ------- | --- | ------ | ----- | ---- | --- |
  |         |     |        |       |      |     |
  |         |     |        |       |      |     |
- Autor
- | id | nom | cogom | pais | any_naixement | foto |
  | -- | --- | ----- | ---- | ------------- | ---- |
  |    |     |       |      |               |      |
  |    |     |       |      |               |      |
-
