<template>
    <div class="stretch" ref="mapCont">
        <vl-map v-if="showMap" ref="map" data-projection="EPSG:4326" @singleclick="mapClick($event)">
            <vl-view :zoom.sync="zoom" :center.sync="center" :rotation.sync="rotation"></vl-view>
            
            <vl-layer-tile>
                <vl-source-osm></vl-source-osm>
            </vl-layer-tile>
            
            <vl-overlay v-show="clickCoord[0] !== 0 && clickCoord[1] !== 0" :position="clickCoord">
                <div class="map-overlay">
                    <h5>{{ selectedOffice.competitorname || selectedOffice.groupname }}</h5>
                    <div>{{ (selectedOffice.country || '-') + (selectedOffice.city ? ', ' + selectedOffice.city : '') }}</div>
                    <div>{{ (selectedOffice.post || '-') + (selectedOffice.address ? ', ' + selectedOffice.address : '') }}</div>
                </div>
            </vl-overlay>
            
            <vl-feature id="markers">
                <template slot-scope="feature">
                    <vl-geom-multi-point v-if="coordinates.length > 0 && zoom > 11"
                                         :coordinates="coordinates">
                    </vl-geom-multi-point>
                    <vl-style-box>
                        <vl-style-icon ref="mapIcon" :src="'../../../content/images/map-pin.svg'" :size="[514, 624]" :scale="0.07" :anchor="[0.5, 1]"></vl-style-icon>
                    </vl-style-box>
                </template>
            </vl-feature>
            
            <vl-feature v-for="area in officeAreas" :key="officeAreas.indexOf(area)" :properties="{offices: area.offices.length}">
                <template slot-scope="scope">
                    <vl-geom-point v-if="zoom <= 11" :coordinates="[area.longitude, area.latitude]"></vl-geom-point>
                    <vl-style-func :factory="clusterStyleFunc"></vl-style-func>
                </template>
            </vl-feature>
        </vl-map>
        <div v-else class="map-disabled">
            <div>Map is disabled for development mode due to it's components event flood inside Vue devtools!
                <br/>
                Set 'MAP_ENABLE = true' to enable it.
                <br/>
                Also stop event recording to prevent performance issues.
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cmp-map.component.ts">
</script>

<style scoped>
    .stretch {
        height: 100%;
        width: 100%;
    }
    
    .map-overlay {
        position: relative;
        background: white;
        padding: 10px;
        border-top-right-radius: 10px;
        border-bottom-left-radius: 10px;
        border-bottom-right-radius: 10px;
        box-shadow: 0 3px 6px grey;
    }
    
    .map-overlay:after {
        content: '';
        width: 0;
        height: 0;
        border-style: solid;
        border-width: 0 0 10px 10px;
        border-color: transparent transparent transparent #ff0000;
        left: 0;
        top: 0;
        position: absolute;
    }
    
    .map-disabled {
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 100%;
        height: 100%;
        text-align: center;
        background-image: linear-gradient(
            to right bottom,
            rgba(255, 255, 255, 0.9),
            rgba(255, 255, 255, 0.9)),
        url(../../../content/images/map-stab.png);
    }
</style>
