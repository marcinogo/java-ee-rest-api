# java-ee-rest-api-oni

## Application usecases

### Basic URL

``localhost:8080/``

### Available resources:

<ol>
  <li><p>Salesmen</p></li>
  <li><p>Showrooms</p></li>
  <li><p>Cars</p></li>
</ol>

### Usecases for Salesmen

#### method GET

User can retrieve all <b>Salesmen</b> from data base in *JSON* by request with specify url as ``localhost:8080/salesmen``.

User can retrieve <b>Salesman</b> with given <b>id</b> number from database in *JSON* by request with specify url as ``localhost:8080/salesmen?{id}``.

#### method POST

User can add new <b>Salesman</b> to data base by send *HTTP request* with data provided in *JSON* type by request with specify url as ``localhost:8080/salesmen``.

Example *HTTP request body*:
<code>
      {
        "name": "John Smith",
        "salary": 1350,
        "birthYear": 1977
    }
</code>

#### method PUT

User can update <b>Salesman</b> with given <b>id</b> number in data base by send *HTTP request* with data provided in *JSON* type by request with specify url as ``localhost:8080/salesmen``.

Example *HTTP request body*:

<code>
    {
        "id": 1,
        "name": "John Smith",
        "salary": 1350,
        "birthYear": 1977
    }
</code>

#### method DELETE

User can delete <b>Salesman</b> with given <b>id</b> number from database by reques with specify url as ``localhost:8080/salesmen?{id}``.

### Usecases for Showrooms

#### method GET

User can retrieve all <b>Showrooms</b> from data base in *JSON* by request with specify url as ``localhost:8080/showrooms``.

User can retrieve <b>Showroom</b> with given <b>id</b> number from database in *JSON* by request with specify url as ``localhost:8080/showrooms?{id}``.

User can retrieve list of <b>Cars</> from <b>Showroom</b> with given <b>id</b> number from database in *JSON* by request with specify url as ``localhost:8080/showrooms?{id}&cars=true``.

#### method POST

User can add new <b>Showroom</b> to data base by send *HTTP request* with data provided in *JSON* type by request with specify url as ``localhost:8080/showrooms``.

Example *HTTP request body*:
<code>
    {
        "name": "Berlin",
        "address": "Schlesischestrasse 22",
    }
</code>

#### method PUT

User can update <b>Showroom</b> with given <b>id</b> number in data base by send *HTTP request* with data provided in *JSON* type by request with specify url as ``localhost:8080/showrooms``.

Example *HTTP request body*:

<code>
    {
        "id": 1,
        "name": "Berlin",
        "address": "Schlesischestrasse 22",
    }
</code>

#### method DELETE

User can delete <b>Showroom</b> with given <b>id</b> number from database by reques with specify url as ``localhost:8080/showrooms?{id}``.

### Usecases for Cars

#### method GET

User can retrieve all <b>Cars</b> with information about <b>Showroom</b> that Car is place from data base in *JSON* by request with specify url as ``localhost:8080/cars``.

User can retrieve <b>Car</b> with given <b>id</b> number from database in *JSON* by request with specify url as ``localhost:8080/car?{id}``.

#### method POST

User can add new <b>Car</b> to data base by send *HTTP request* with data about Car and <b>Showroom</b> that it is placed by provide it in *JSON* type by request with specify url as ``localhost:8080/cars``.

Example *HTTP request body*:
<code>
    {
        "manufacturer": "Opel",
        "model": "Zafira",
        "color": "red",
        "yearOfProduction": 2010,
        "showroom": {
            "id": 5,
            "name": "Moscow",
            "address": "Babilowa 57"
        }
    }
</code>

#### method PUT

User can ipdate <b>Car</b> in data base by send *HTTP request* with data about Car and <b>Showroom</b> that it is placed by provide it in *JSON* type by request with specify url as ``localhost:8080/cars``.

Example *HTTP request body*:

<code>
    {
        "id": 1,
        "manufacturer": "Opel",
        "model": "Zafira",
        "color": "red",
        "yearOfProduction": 2010,
        "showroom": {
            "id": 5,
            "name": "Moscow",
            "address": "Babilowa 57"
        }
    }
</code>

#### method DELETE

User can delete <b>Car</b> with given <b>id</b> number from database by reques with specify url as ``localhost:8080/car?{id}``.
