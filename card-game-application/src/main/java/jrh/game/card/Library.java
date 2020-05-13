package jrh.game.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jrh.game.card.behaviour.DamageBehaviour;
import jrh.game.card.behaviour.DrawBehaviour;
import jrh.game.card.behaviour.MoneyBehaviour;
import jrh.game.util.Color;
import jrh.game.util.ObjectMapperFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Library {

    private static final Logger logger = LogManager.getLogger(Library.class);

    public static final List<Card> CARDS;
    static {
        List<Card> cards = new ArrayList<>();
        cards.addAll(moneyCards());
        cards.addAll(damageCards());
        cards.addAll(drawCards());
        CARDS = Collections.unmodifiableList(cards);
    }

    private final List<Card> cards;

    public Library(File library) {
        this.cards = loadCards(library);
    }

    public static void dumpCards(File library) {
        ObjectWriter objectWriter = ObjectMapperFactory.create().writerWithDefaultPrettyPrinter();
        for (Card card : CARDS) {
            try {
                objectWriter.writeValue(Paths.get(library.getAbsolutePath(), card.getId().toString() + ".json").toFile(), card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Card> loadCards(File library) {
        ObjectMapper objectMapper = ObjectMapperFactory.create();
        List<Card> cards = new ArrayList<>();
        addCardsFromDirectory(library, cards, objectMapper);
        return cards;
    }

    private void addCardsFromDirectory(File directory, List<Card> cardList, ObjectMapper objectMapper) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isHidden()) {
                continue;
            }
            if (file.isDirectory()) {
                addCardsFromDirectory(file, cardList, objectMapper);
            } else {
                try {
                    cardList.add(objectMapper.readValue(file, Card.class));
                } catch (IOException e) {
                    logger.error("Error reading card from file={}", file, e);
                }
            }
        }
    }

    public static Card getCard(String name) {
        Optional<Card> optionalCard = CARDS.stream().filter(card -> card.getName().equals(name)).findFirst();
        if (optionalCard.isPresent()) {
            return optionalCard.get();
        }
        throw new RuntimeException("No card with name: " + name);
    }

    private static List<Card> moneyCards() {
        return List.of(
                Card.card(new CardId("copper")).withName("Copper").withCost(1).withColor(Color.YELLOW).withBehaviour(new MoneyBehaviour(1)).build(),
                Card.card(new CardId("silver")).withName("Silver").withCost(3).withColor(Color.YELLOW).withBehaviour(new MoneyBehaviour(2)).build(),
                Card.card(new CardId("gold")).withName("Gold").withCost(6).withColor(Color.YELLOW).withBehaviour(new MoneyBehaviour(3)).build()
        );
    }

    private static List<Card> damageCards() {
        return List.of(
                Card.card(new CardId("knife")).withName("Knife").withCost(1).withColor(Color.RED).withBehaviour(new DamageBehaviour(1)).build(),
                Card.card(new CardId("sword")).withName("Sword").withCost(3).withColor(Color.RED).withBehaviour(new DamageBehaviour(3)).build()
        );
    }

    private static List<Card> drawCards() {
        return List.of(
                Card.card(new CardId("draw-1")).withName("Draw 1").withCost(3).withColor(Color.BLUE).withBehaviour(new DrawBehaviour(1)).build(),
                Card.card(new CardId("draw-2")).withName("Draw 2").withCost(5).withColor(Color.BLUE).withBehaviour(new DrawBehaviour(2)).build(),
                Card.card(new CardId("draw-money"))
                        .withName("Double dip")
                        .withCost(8)
                        .withColor(Color.GREEN)
                        .withBehaviour(new MoneyBehaviour(2))
                        .withBehaviour(new DrawBehaviour(1))
                        .build()
        );
    }
}
