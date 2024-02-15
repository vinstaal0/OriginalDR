package nl.vinstaal0.Dungeonrealms.Monstermechanics.Converstation;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import static java.lang.Integer.parseInt;

public class SpawnRange extends StringPrompt {

    @Override
    public String getPromptText(ConversationContext conversationContext) {
        return ChatColor.RED + "Please enter the spawn range (ex: 1-5):";
    }


    @Override
    public Prompt acceptInput(ConversationContext conversationContext, String input) {

        try {
            String[] parts = input.split("-");

            int lowerRange = Integer.parseInt(parts[0]);
            int upperRange = Integer.parseInt(parts[1]);


            int[] range = new int[] {lowerRange, upperRange};

            conversationContext.setSessionData("spawnRange", range);
            return Prompt.END_OF_CONVERSATION;

        } catch (NumberFormatException exception) {
            conversationContext.getForWhom().sendRawMessage(ChatColor.RED + "Invalid input. Please enter two numbers seperated by a dash (ex: 1-5):");
            return this;
        }
    }
}
