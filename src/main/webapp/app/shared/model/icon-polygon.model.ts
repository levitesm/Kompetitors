export class IconPolygon {
  public topLeftX?: number;
  public topLeftY?: number;
  public bottomRightX?: number;
  public bottomRightY?: number;

  constructor(topLeftX: number, topLeftY: number, bottomRightX: number, bottomRightY: number) {
    this.topLeftX = topLeftX;
    this.topLeftY = topLeftY;
    this.bottomRightX = bottomRightX;
    this.bottomRightY = bottomRightY;
  }

  public isPointInside(x: number, y: number): boolean {
    return x >= this.topLeftX && x <= this.bottomRightX && y >= this.topLeftY && y <= this.bottomRightY;
  }

  public isPolygonOverlap(polygon: IconPolygon): boolean {
    return (
      this.isPointInside(polygon.topLeftX, polygon.topLeftY) ||
      this.isPointInside(polygon.bottomRightX, polygon.topLeftY) ||
      this.isPointInside(polygon.bottomRightX, polygon.bottomRightY) ||
      this.isPointInside(polygon.topLeftX, polygon.bottomRightY)
    );
  }
}
