package ga.guimx.gAbility.utils;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class AbilityArgument extends ArgumentResolver<CommandSender, Ability> {
    @Override
    protected ParseResult<Ability> parse(
            Invocation<CommandSender> invocation,
            Argument<Ability> argument,
            String string
    ){
        Ability ability;
        try {
            ability = new Ability(AbilityType.fromString(string));
        } catch (IllegalArgumentException e){
            return ParseResult.failure("Invalid ability");
        }
        return ParseResult.success(ability);
    }

    @Override
    public SuggestionResult suggest(
            Invocation<CommandSender> invocation,
            Argument<Ability> argument,
            SuggestionContext context
    ){
        return Arrays.stream(AbilityType.values()).map(Enum::toString).collect(SuggestionResult.collector());
    }
}
