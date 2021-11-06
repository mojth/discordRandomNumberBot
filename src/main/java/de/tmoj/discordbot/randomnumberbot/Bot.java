package de.tmoj.discordbot.randomnumberbot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot extends ListenerAdapter {

    private static final String PREFIX = "!random";
    private static final String BULB_EMOJI = "\uD83D\uDCA1";
    private MessageEmbed help;

    public static void main(String[] args) throws LoginException {
        if (args.length != 1) {
            System.out.println("Usage: Provide the Token as first and only argument");
            System.exit(1);
        }
        JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES).addEventListeners(new Bot()).build();
    }

    public Bot() {
        help = new EmbedBuilder().setTitle(BULB_EMOJI + " Usage") //
                .setDescription("Provide a max value for your random number \n" //
                        + "e. g. '!random 10' for a random number between 1 and 10") //
                .setColor(0xfcda00).build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String contentRaw = event.getMessage().getContentRaw();
        if (!contentRaw.startsWith(PREFIX)) {
            return;
        }
        String args[] = contentRaw.split("\\s");
        if (args.length == 2 && args[1].matches("\\d+")) {
            int maxValue = Integer.parseInt(args[1]);
            int randomNumber = (int) (Math.random() * maxValue) + 1;
            event.getChannel().sendMessage("Your random number: " + randomNumber).queue();
        } else {
            event.getChannel().sendMessageEmbeds(help).queue();
        }
    }
}
