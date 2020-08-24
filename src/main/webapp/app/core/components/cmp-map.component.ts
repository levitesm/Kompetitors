import { Component, Prop, Vue, Watch } from 'vue-property-decorator';
import {
  Map,
  TileLayer,
  OsmSource,
  MultiPointGeom,
  Feature,
  IconStyle,
  StyleBox,
  Overlay,
  CircleStyle,
  PointGeom,
  StyleFunc
} from 'vuelayers';
import { OfficeSearchDTO } from '@/shared/model/office-search.model';
import { OfficeArea } from '@/shared/model/office-area.model';
import { IconPolygon } from '@/shared/model/icon-polygon.model';

import { createStyle } from 'vuelayers/lib/ol-ext';

Vue.use(Map);
Vue.use(TileLayer);
Vue.use(OsmSource);
Vue.use(MultiPointGeom);
Vue.use(Feature);
Vue.use(IconStyle);
Vue.use(StyleBox);
Vue.use(Overlay);
Vue.use(CircleStyle);
Vue.use(PointGeom);
Vue.use(StyleFunc);

@Component
export default class CmpMap extends Vue {
  @Prop() public offices: OfficeSearchDTO[];

  public zoom = 1;
  public center = [0, 0];
  public rotation = 0;
  public clickCoord = [0, 0];
  public selectedOffice: OfficeSearchDTO = OfficeSearchDTO.empty();

  // Map is disabled for development mode by default due to Vue devtools event flood
  public MAP_ENABLE = false;

  $refs!: {
    map: any;
    mapCont: any;
    mapIcon: any;
  };

  @Watch('offices')
  onSearch() {
    this.clickCoord = [0, 0];
    this.centerMap();
  }

  public get showMap(): boolean {
    const isDevMode = this.$store.getters.activeProfiles.includes('dev');
    return isDevMode ? this.MAP_ENABLE : true;
  }

  public get icon(): any {
    return {
      height: this.$refs.mapIcon.$props.size[1] * this.$refs.mapIcon.$props.scale,
      width: this.$refs.mapIcon.$props.size[0] * this.$refs.mapIcon.$props.scale
    };
  }

  public get coordinates(): number[][] {
    return this.offices.reduce((result, office) => {
      if (office.longitude && office.latitude) {
        result.push([Number(office.longitude), Number(office.latitude)]);
      }
      return result;
    }, []);
  }

  public get officeAreas(): OfficeArea[] {
    const areas: OfficeArea[] = [];
    this.offices.forEach(office => {
      let same = null;
      for (const area in areas) {
        if (areas.hasOwnProperty(area)) {
          let found = false;
          for (const areaOffice in areas[area].offices) {
            if (this.isSameArea(office, areas[area].offices[areaOffice])) {
              same = area;
              found = true;
              break;
            }
          }
          if (found) {
            break;
          }
        }
      }
      if (same) {
        areas[same].offices.push(office);
      } else {
        const newArea = OfficeArea.empty();
        newArea.offices.push(office);
        areas.push(newArea);
      }
    });
    return this.findAreaCenter(areas);
  }

  private findAreaCenter(areas: OfficeArea[]): OfficeArea[] {
    return areas.map(area => {
      if (area.offices.length > 1) {
        const sum = area.offices.reduce(
          (result, office) => {
            return [(result[0] += office.longitude), (result[1] += office.latitude)];
          },
          [0, 0]
        );
        const center = [sum[0] / area.offices.length, sum[1] / area.offices.length];
        area.longitude = center[0];
        area.latitude = center[1];
      } else if (area.offices.length === 1) {
        area.longitude = area.offices[0].longitude;
        area.latitude = area.offices[0].latitude;
      }
      return area;
    });
  }

  public clusterStyleFunc() {
    return function __clusterStyleFunc(feature) {
      const size = feature.get('offices');
      return createStyle({
        imageRadius: 20,
        strokeColor: '#fff',
        fillColor: '#6b48ff',
        text: size.toString(),
        // textFontSize: 15,
        textOffsetY: 2,
        textFont: '300 15px monospace',
        textFillColor: '#fff'
      });
    };
  }

  public isSameArea(o1: OfficeSearchDTO, o2: OfficeSearchDTO): boolean {
    const p1 = this.getPolygon(o1);
    const p2 = this.getPolygon(o2);
    return p1.isPolygonOverlap(p2) || p2.isPolygonOverlap(p1);
  }

  public getPolygon(office: OfficeSearchDTO): IconPolygon {
    const bottom = this.$refs.map.getPixelFromCoordinate([office.longitude, office.latitude]);
    if (!bottom) {
      return new IconPolygon(0, 0, 0, 0);
    }
    const topLeft = [bottom[0] - this.icon.width / 2, bottom[1] - this.icon.height];
    const bottomRight = [bottom[0] + this.icon.width / 2, bottom[1]];
    return new IconPolygon(topLeft[0], topLeft[1], bottomRight[0], bottomRight[1]);
  }

  public centerMap() {
    const max = [Number.MIN_VALUE, Number.MIN_VALUE];
    const min = [Number.MAX_VALUE, Number.MAX_VALUE];
    if (this.coordinates.length > 1) {
      this.coordinates.forEach(pair => {
        max[0] = pair[0] >= max[0] ? pair[0] : max[0];
        min[0] = pair[0] <= min[0] ? pair[0] : min[0];
        max[1] = pair[1] >= max[1] ? pair[1] : max[1];
        min[1] = pair[1] <= min[1] ? pair[1] : min[1];
      });
      const zoomX = Math.log2(360 / (max[0] - min[0]));
      const zoomY = Math.log2(180 / (max[1] - min[1]));
      this.zoom = Math.min(zoomX, zoomY);
      this.center = [(max[0] + min[0]) / 2, (max[1] + min[1]) / 2];
    } else if (this.coordinates.length === 1) {
      this.center = [this.coordinates[0][0], this.coordinates[0][1]];
      this.zoom = 15;
    } else {
      this.center = [0, 0];
      this.zoom = 1;
    }
    if (this.showMap) {
      this.$refs.map.updateSize();
    }
  }

  public selectOffice(point: number[]): void {
    if (this.offices.length > 0) {
      let closest = this.offices[0];
      this.offices
        .filter(office => office.latitude < point[1])
        .forEach(office => {
          if (
            Math.pow(Math.pow(office.longitude - point[0], 2) + Math.pow(office.latitude - point[1], 2), 1 / 2) <
            Math.pow(Math.pow(closest.longitude - point[0], 2) + Math.pow(closest.latitude - point[1], 2), 1 / 2)
          ) {
            closest = office;
          }
        });
      this.clickCoord = [closest.longitude, closest.latitude];
      this.selectedOffice = closest;
    } else {
      this.selectedOffice = OfficeSearchDTO.empty();
      this.clickCoord = [0, 0];
    }
  }

  public mapClick(event) {
    const feature = this.$refs.map.forEachFeatureAtPixel(event.pixel, function(feat) {
      return feat;
    });
    if (feature && feature.id_ === 'markers') {
      this.selectOffice(event.coordinate);
    } else {
      this.selectedOffice = OfficeSearchDTO.empty();
      this.clickCoord = [0, 0];
    }
  }
}
