export const card = {
  WIDTH: 144,
  FULL_HEIGHT: 212,
  BORDER_WIDTH: 3,
  BORDER_RADIUS: 3,
  MARGIN_TOP_BOTTOM: 12,
  MARGIN_LEFT_RIGHT: 8,
  PADDING: 5,
};

const INNER_CARD_WIDTH = card.WIDTH - 2 * (card.PADDING + card.BORDER_WIDTH);
const INNER_CARD_HEIGHT =
  card.FULL_HEIGHT - 2 * (card.PADDING + card.BORDER_WIDTH);

export const header = {
  HEIGHT: 32,
  WIDTH: INNER_CARD_WIDTH,
  MARGIN_BOTTOM: card.PADDING,
  LINE_HEIGHT: 16,
};

export const cost = {
  RADIUS: 14,
};

export const image = {
  WIDTH: INNER_CARD_WIDTH,
  HEIGHT: 96,
  MARGIN_BOTTOM: card.PADDING,
};

export const description = {
  WIDTH: INNER_CARD_WIDTH,
  HEIGHT:
    INNER_CARD_HEIGHT -
    (header.HEIGHT + header.MARGIN_BOTTOM) -
    (image.HEIGHT + image.MARGIN_BOTTOM),
  MARGIN_BOTTOM: card.PADDING,
};