package uk.co.hd_tech.openstf;

import org.apache.commons.cli.*;

import java.util.Iterator;
import java.util.List;

public class CLI {

    public static void main(String[] args) {
        Options options = getOptions();
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.getOptions().length == 0) {
                showHelp(options);
                return;
            }

            CommandHandler commandHandler = new CommandHandler();

            String url = null;
            String token = null;

            for (Option option : cmd.getOptions()) {
                if ("t".equals(option.getOpt()) || "token".equals(option.getLongOpt())) {
                    token = option.getValue();
                }

                if ("u".equals(option.getOpt()) || "url".equals(option.getLongOpt())) {
                    url = option.getValue();
                }
            }

            for (Option option : cmd.getOptions()) {
                if ("status".equals(option.getOpt())) {
                    commandHandler.status(url, token);
                } else if ("connect".equals(option.getOpt())) {
                    commandHandler.connect(url, token);
                } else if ("disconnect".equals(option.getOpt())) {
                    commandHandler.disconnect(url, token);
                }
            }
        } catch (MissingOptionException e) {
            showRequiredOptions(e.getMissingOptions());
            showHelp(options);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static Options getOptions() {
        Options options = new Options();

        options.addRequiredOption("u", "url", true, "OpenSTF url");
        options.addRequiredOption("t", "token", true, "OpenSTF token");

        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(new Option("status", "Show status of devices on OpenSTF server"));
        optionGroup.addOption(new Option("connect", "Connect any available devices"));
        optionGroup.addOption(new Option("disconnect", "Disconnect from OpenSTF"));
        optionGroup.setRequired(true);

        options.addOptionGroup(optionGroup);

        return options;
    }

    public static void showHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("openstf -u url -t token -status|-connect|-disconnect", options);
    }

    public static void showRequiredOptions(List missingOptions) {
        StringBuilder buf = new StringBuilder("Missing required option");
        buf.append(missingOptions.size() == 1 ? "" : "s");
        buf.append(": ");

        Iterator<?> it = missingOptions.iterator();
        while (it.hasNext()) {
            buf.append(it.next());
            if (it.hasNext()) {
                buf.append(", ");
            }
        }

        System.out.println(buf.toString());
    }
}
