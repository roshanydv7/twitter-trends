import com.twitter.config.CustomLogger;
import com.twitter.constants.TwitterConstants;
import com.twitter.exceptions.TwitterApiException;
import com.twitter.utils.ObjectUtils;
import com.twitter.utils.ReaderUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TwitterTrends {

    private static final Logger LOGGER = CustomLogger.getLogger(TwitterTrends.class);

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            LOGGER.info(TwitterConstants.INIT_MESSAGE);

            String inputLines = reader.readLine();
            if (ObjectUtils.isNumber(inputLines)) {
                int type = Integer.parseInt(inputLines);

                if (type == 1) {
                    readInputContents(reader);
                } else if (type == 2) {
                    readFileContents(reader);
                }
            } else {
                LOGGER.fine(TwitterConstants.INVALID_INPUT);
            }
        } catch (Exception exec) {
            LOGGER.fine(TwitterConstants.ERROR_OCCURRED + exec);
        }
    }

    /**
     * @param reader **used to read content from console input"
     * @throws IOException ** throws exception
     */
    private static void readInputContents(BufferedReader reader) throws IOException {
        LOGGER.info(TwitterConstants.ENTER_NO_OF_LINES);
        String firstInput = reader.readLine();
        if (ObjectUtils.isNumber(firstInput)) {
            int secondInput = Integer.parseInt(firstInput);
            LOGGER.info(TwitterConstants.ENTER_CONTENT);
            List<String> contents = new ArrayList<>();

            for (int i = 0; i < secondInput; i++) {
                String line = reader.readLine();
                if (ObjectUtils.isNotEmpty(line)) {
                    contents.add(line);
                }
            }
            viewContent(contents);
        } else {
            LOGGER.fine(TwitterConstants.INVALID_INPUT);
        }
    }

    /**
     * @param reader **used to read content from files"
     * @throws IOException ** throws exception
     */
    private static void readFileContents(BufferedReader reader) throws IOException {
        LOGGER.info(TwitterConstants.ENTER_FILE_NAME);
        String line = reader.readLine();
        if (ObjectUtils.isNotEmpty(line)) {
            viewContent(Collections.singletonList(getContent(line)));
        } else {
            throw new TwitterApiException(TwitterConstants.FILE_NAME_EMPTY);
        }
    }

    /**
     * @param content ** use to read content**
     */
    private static void viewContent(List<String> content) {
        LinkedHashMap<String, Integer> hashTagCounterMap = countHashTag(content);
        if (!hashTagCounterMap.isEmpty()) {
            hashTagCounterMap.entrySet().stream().sorted(compare()).limit(10).collect(Collectors.toList()).forEach(e -> LOGGER.info(e.getKey()));
        } else {
            LOGGER.info(TwitterConstants.NO_TRENDS);
        }
    }

    /**
     * @param fileName **pass the fileName**
     * @return content **get the content**
     * @throws IOException **throws exception**
     */

    private static String getContent(String fileName) throws IOException {
        // String content = ReaderUtils.readUsingFile(fileName);
        String content = ReaderUtils.readUsingBufferedReader(fileName);
        if (ObjectUtils.isNotEmpty(content)) {
            return content;
        } else {
            throw new TwitterApiException(TwitterConstants.NO_CONTENT);
        }
    }

    /**
     * @param content **content list**
     * @return count **get the count hash tags**
     */

    private static LinkedHashMap<String, Integer> countHashTag(List<String> content) {
        LinkedHashMap<String, Integer> hashTagCounterMap = new LinkedHashMap<>();
        Pattern pattern = Pattern.compile(TwitterConstants.WORD_REGEX);
        Matcher matcher;
        for (String word : content) {
            matcher = pattern.matcher(word);

            while (matcher.find()) {
                if (Objects.nonNull(hashTagCounterMap.get(matcher.group())))
                    hashTagCounterMap.put(matcher.group(), hashTagCounterMap.get(matcher.group()) + 1);
                else
                    hashTagCounterMap.put(matcher.group(), 1);
            }
        }

        return hashTagCounterMap;
    }

    /**
     * @return Comparator **order the objects**
     */
    private static Comparator<Map.Entry<String, Integer>> compare() {
        return (o1, o2) -> {
            if (o2.getValue().equals(o1.getValue()))
                return o1.getKey().compareTo(o2.getKey());
            else
                return o2.getValue().compareTo(o1.getValue());
        };
    }
}