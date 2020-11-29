export const card = {
  WIDTH: 132,
  FULL_HEIGHT: 192,
  BORDER_RADIUS: 3,
  MARGIN_TOP_BOTTOM: 10,
  MARGIN_LEFT_RIGHT: 8,
  PADDING: 4,
  STUMP_HEIGHT: 35,
};

const INNER_CARD_WIDTH = card.WIDTH - 2 * card.PADDING;
const INNER_CARD_HEIGHT = card.FULL_HEIGHT - 2 * card.PADDING;

export const header = {
  HEIGHT: 36,
  WIDTH: INNER_CARD_WIDTH,
  MARGIN_BOTTOM: card.PADDING,
  LINE_HEIGHT: 16,
  FONT_SIZE: 0.9,
};

export const cost = {
  RADIUS: 14,
};

export const image = {
  WIDTH: INNER_CARD_WIDTH,
  HEIGHT: 68,
  MARGIN_BOTTOM: card.PADDING,
};

export const description = {
  WIDTH: INNER_CARD_WIDTH,
  HEIGHT:
    INNER_CARD_HEIGHT -
    (header.HEIGHT + header.MARGIN_BOTTOM) -
    (image.HEIGHT + image.MARGIN_BOTTOM),
  MARGIN_BOTTOM: card.PADDING,
  FONT_SIZE: 0.88,
};
