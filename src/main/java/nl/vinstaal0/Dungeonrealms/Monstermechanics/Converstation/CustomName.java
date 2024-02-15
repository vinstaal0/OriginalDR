package nl.vinstaal0.Dungeonrealms.Monstermechanics.Converstation;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class CustomName extends StringPrompt {


    @Override
    public String getPromptText(ConversationContext conversationContext) {
        return ChatColor.RED + "Please enter the custom name you want the mob to have.";
    }

    @Override
    public Prompt acceptInput(ConversationContext conversationContext, String input) {
        conversationContext.setSessionData("customName", input);

        return Prompt.END_OF_CONVERSATION;
    }
}
