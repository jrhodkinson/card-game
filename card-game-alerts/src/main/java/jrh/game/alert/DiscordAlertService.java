package jrh.game.alert;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.security.auth.login.LoginException;

public class DiscordAlertService implements AlertService {

    private final JDA jda;

    public DiscordAlertService(String discordToken) throws LoginException, InterruptedException {
        this.jda = JDABuilder.createDefault(discordToken).build();
        jda.awaitReady();
    }

    @Override
    public void sendAlert(String channel, String message) {
        MessageChannel messageChannel = jda.getTextChannelsByName(channel, true).get(0);
        messageChannel.sendMessage(message).queue();
    }
}
