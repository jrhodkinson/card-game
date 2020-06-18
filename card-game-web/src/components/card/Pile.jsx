const Pile = ({ cards }) => {
  return cards.length === 0 ? "Empty" : cards[cards.length - 1].name;
};

export default Pile;
