<template>
    <div class="map">
        <h1>Mapa emergencias</h1>
        <v-select v-model="id_emergencia" :items="emergencias" item-text="nombre" item-value="_id" label="Emergencia"
            required>
        </v-select>
        <v-btn color="blue darken-1" text @click="mostrarPuntos">
            Mostrar Ubicaciones
        </v-btn>

        <div>{{ message }}</div>
        <div id="mapid"></div>
    </div>
</template>

<script>
//Importaciones
import 'leaflet/dist/leaflet'; //librería leaflet
import 'leaflet/dist/leaflet.css'; //css leaflet
var icon = require('leaflet/dist/images/marker-icon.png'); //ícono de marcadores
//require icon from static
var icon2 = require('../../static/redpin2.png');
//Se crea objeto ícono con el marcador
var LeafIcon = L.Icon.extend({
    options: { iconSize: [25, 41], iconAnchor: [12, 41], popupAnchor: [-3, -41] }
});
var LeafIcon2 = L.Icon.extend({
    options: { iconSize: [30, 50], iconAnchor: [12, 41], popupAnchor: [-3, -41] }
});
var myIcon = new LeafIcon({ iconUrl: icon });
var myIcon2 = new LeafIcon2({ iconUrl: icon2 });

//librería axios
import axios from 'axios';
export default {
    name: 'map',
    data: function () {
        return {
            latitud: null, //Datos de nuevo punto
            longitud: null,
            name: '',
            points: [], //colección de puntos cargados de la BD
            message: '',
            mymap: null, //objeto de mapa(DIV)
            emergencias: [],
            id_emergencia: -1,
            coordenadas: []
        }
    },
    computed: {
        point() { // función computada para representar el punto seleccionado
            if (this.latitud && this.longitud) {
                let lat = this.latitud.toFixed(3);
                let lon = this.longitud.toFixed(3);
                return `(${lat}, ${lon})`;
            } else {
                return '';
            }
        }
    },
    methods: {
        mostrarPuntos() {
            if (this.id_emergencia == -1) {
                this.message = "Debe seleccionar una emergencia";
            } else {
                this.coordenadas = this.emergencias[this.id_emergencia].geom.coordinates;
                this.clearMarkers()
                //Se agregan los puntos mediante llamada al servicio
                this.getPoints(this.mymap);
            }
        },
        async getEmergencies() {
            const url = "http://localhost:8090/emergencies/locations";
            await axios.get(url)
                .then((response) => {
                    this.emergencias = response.data.sort((a, b) => a._id - b._id)
                })
                .catch((error) => {
                    console.log(error)
                })
        },
        clearMarkers: function () { //eliminar marcadores

            this.points.forEach(p => {
                this.mymap.removeLayer(p);
            })
            this.points = [];
        },
        async getPoints(map) {
            try {
                //se llama el servicio 
                let lon = this.coordenadas[0]
                let lat = this.coordenadas[1]
                const url = 'http://localhost:8090/volunteers/near/' + String(lon) + '/' + String(lat);
                let response = await axios.get(url);
                let dataPoints = response.data;

                // Punto de la emergencia
                let pE = [lat, lon]
                let infoE = this.emergencias[this.id_emergencia].nombre;
                let markerE = L.marker(pE, { icon: myIcon2 }).bindPopup(infoE);
                this.points.push(markerE);

                // Radio de la emergencia
                var circle = L.circle([lat, lon], {
                    color: 'red',
                    fillColor: '#f03',
                    fillOpacity: 0.5,
                    radius: 500000
                })
                this.points.push(circle)

                //Se itera por los puntos
                dataPoints.forEach(point => {
                    let info = point.nombre + ", " + point.fnacimiento + ", " + point.rut + ", " + point.geom.coordinates[0] + ", " + point.geom.coordinates[1]
                    //Se crea un marcador por cada punto
                    let p = [point.geom.coordinates[1], point.geom.coordinates[0]]
                    let marker = L.marker(p, { icon: myIcon }) //se define el ícono del marcador
                        .bindPopup(info); //Se agrega un popup con el nombre

                    //Se agrega a la lista
                    this.points.push(marker);
                });

                //Los puntos de la lista se agregan al mapa
                this.points.forEach(p => {
                    p.addTo(map)
                })
            } catch (error) {
                console.log('error', error);
            }

        }
    },
    mounted: function () {
        this.getEmergencies()
        let _this = this;
        //Se asigna el mapa al elemento con id="mapid"
        this.mymap = L.map('mapid').setView([-33.456, -70.648], 7);
        //Se definen los mapas de bits de OSM
        L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
            maxZoom: 10
        }).addTo(this.mymap);

        //Evento click obtiene lat y long actual
        /* this.mymap.on('click', function(e) {
          _this.latitud = e.latlng.lat;
          _this.longitud =e.latlng.lng;
        }); */
    }
}
</script>
<style>
.map {
    display: flex;
    flex-direction: column;
    align-items: center;
    position: relative;
    z-index: 0;
}

/* Estilos necesarios para definir el objeto de mapa */
#mapid {
    height: 400px;
    width: 600px;
}
</style>