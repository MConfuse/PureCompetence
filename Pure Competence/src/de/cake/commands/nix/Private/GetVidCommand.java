package de.cake.commands.nix.Private;

import java.time.OffsetDateTime;
import java.util.concurrent.ThreadLocalRandom;

import de.cake.PureCompetence;
import de.cake.commands.types.PrivateCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;

public class GetVidCommand implements PrivateCommand {

	@Override
	public void performCommand(User u, PrivateChannel channel, Message message) {
		EmbedBuilder builder = new EmbedBuilder();

		int randomNum = ThreadLocalRandom.current().nextInt(14, 376981 + 1);
		if (u.getIdLong() == 231777358116421633l || u.getIdLong() == 255313111391666176l) {

			builder.setTitle("Here's a Video ;)", "https://www.faproulette.org/video/" + randomNum);
			builder.setColor(PureCompetence.INSTANCE.clrViolet);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
		} else {
			builder.setTitle("Unknown Command");
			builder.setDescription("Please try '" + PureCompetence.INSTANCE.prefix + "help' for more info");
			builder.setColor(PureCompetence.INSTANCE.clrRed);
			builder.setFooter(PureCompetence.INSTANCE.pwrdBy);
			builder.setTimestamp(OffsetDateTime.now());
			channel.sendMessage(builder.build()).queue();
		}

	}

}
