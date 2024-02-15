package nl.vinstaal0.Dungeonrealms.Monstermechanics.Converstation;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class Amount extends StringPrompt {
    @Override
    public String getPromptText(ConversationContext conversationContext) {
        return ChatColor.RED + "Please enter the amount that needs to spawn:";
    };

    @Override
    public Prompt acceptInput(ConversationContext conversationContext, String input) {
        try {
            int amount = Integer.parseInt(input);
            conversationContext.setSessionData("amount", amount);
            return Prompt.END_OF_CONVERSATION;
        } catch (NumberFormatException e) {
            conversationContext.getForWhom().sendRawMessage(ChatColor.RED + "Invalid input. Please enter a number.");
            return this;
        }
    }
}
