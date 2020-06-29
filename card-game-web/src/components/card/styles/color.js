import { adjustHue, darken, lighten } from "polished";

const colors = {
  RED: "#F88",
  YELLOW: "#FF8",
  BLUE: "#88F",
  WHITE: "#FFF",
  GREEN: "#8F8",
  CYAN: "#AFF",
};

export const cardColor = (name) => colors[name] || "#F8F";

export const darkCardColor = (name) =>
  adjustHue(355, darken(0.1, cardColor(name)));

export const lightCardColor = (name) =>
  adjustHue(5, lighten(0.12, cardColor(name)));

export const FOREGROUND = "rgba(255, 255, 255, 0.7)";

export const BORDER = "#444";
