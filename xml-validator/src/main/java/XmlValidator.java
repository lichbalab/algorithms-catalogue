import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * <p>
 * Time: O(n)
 * Memory: O(n)
 */
public class XmlValidator {

    public static void main(String[] args) throws IOException {
        BufferedReader reader    = new BufferedReader(new InputStreamReader(System.in));
        String         brokenXml = reader.readLine();


        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(recoverXml(brokenXml));
        writer.close();
        reader.close();
    }

    private static String recoverXml(String brokenXml) {
        char[] chars = brokenXml.toCharArray();

        int openTag = 0;
        int closeTag = 0;
        for (int i = 0; i < chars.length - 1; i++) {
            char ch1 = chars[i];
            char ch2 = chars[i + 1];

            if (ch1 == '<' && Character.isLetter(ch2)) {
                openTag++;
            } else if (ch1 == '<' && ch2 == '/') {
                closeTag++;
            }
        }

        for (int i = 0; i < chars.length - 1; i++) {
            char ch1 = chars[i];
            char ch2 = chars[i + 1];

            if (ch1 == '<' && ch2 != '/' && !Character.isLetter(ch2)) {
                String xml = getCorrectXml(replaceCharAtPosition(brokenXml, '*', i + 1));
                if (xml != null) {
                    return xml;
                }

                return getCorrectXml(replaceCharAtPosition(brokenXml, '/', i + 1));
            } else if (ch1 == '>' && ch2 != '<') {
                String xml = getCorrectXml(replaceCharAtPosition(brokenXml, '*', i));
                if (xml != null) {
                    return xml;
                }

                xml = getCorrectXml(replaceCharAtPosition(brokenXml, '<', i));
                if (xml != null) {
                    return xml;
                }

                return getCorrectXml(replaceCharAtPosition(brokenXml, '<', i + 1));
            } else if (ch1 == '/' && !Character.isLetter(ch2)) {
                String xml = getCorrectXml(replaceCharAtPosition(brokenXml, '*', i + 1));
                if (xml != null) {
                    return xml;
                }

                return getCorrectXml(replaceCharAtPosition(brokenXml, '*', i));
            } else if (Character.isLetter(ch1) && !Character.isLetter(ch2) && ch2 != '>') {
                String xml = getCorrectXml(replaceCharAtPosition(brokenXml, '*', i + 1));
                if (xml != null) {
                    return xml;
                }

                if (ch2 == '/') {
                    xml = getCorrectXml(replaceCharAtPosition(brokenXml, '<', i));
                    if (xml != null) {
                        return xml;
                    }
                } else if (ch2 == '<') {
                    xml = getCorrectXml(replaceCharAtPosition(brokenXml, '>', i));
                    if (xml != null) {
                        return xml;
                    }
                }
                return getCorrectXml(replaceCharAtPosition(brokenXml, '>', i + 1));
            } else if (ch1 != '<' && i == 0) {
                String xml = getCorrectXml(replaceCharAtPosition(brokenXml, '<', i));
                if (xml != null) {
                    return xml;
                }
            } else if (ch1 == '/' && Character.isLetter(ch2) && openTag != closeTag) {
                String xml = getCorrectXml(replaceCharAtPosition(brokenXml, '*', i));
                if (xml != null) {
                    return xml;
                }
            } else if (ch1 == '<' && Character.isLetter(ch2)) {
                String xml = getCorrectXml(replaceCharAtPosition(brokenXml, '/', i + 1));
                if (xml != null) {
                    return xml;
                }
            }
        }
        return getCorrectXml(brokenXml);
    }

    private static String getCorrectXml(String originalXml) {
        return getCorrectXml(originalXml, 0);
    }

    private static String getCorrectXml(String originalXml, int countOfChanges) {
        char[]                 chars    = originalXml.toCharArray();
        Stack<CharIndex>       stack    = new Stack<>();
        Stack<List<CharIndex>> tagStack = new Stack<>();
        int                    i        = 0;
        List<CharIndex>        tag      = new ArrayList<>();

        while (i < chars.length) {
            char ch = chars[i];

            if (ch == '<') {
                tag = new ArrayList<>();
                stack.add(new CharIndex(ch, i));
            } else if (Character.isLetter(ch) || ch == '*') {
                tag.add(new CharIndex(ch, i));
            } else if (ch == '/') {
                if (stack.isEmpty()) {
                    return null;
                }

                CharIndex chi = stack.pop();

                if (chi.ch != '<') {
                    return null;
                }

                if (stack.isEmpty()) {
                    return null;
                }

                chi = stack.pop();

                if (chi.ch != '>') {
                    return null;
                }

                if (tagStack.isEmpty()) {
                    return null;
                }

                List<CharIndex> list = tagStack.pop();

                for (CharIndex item : list) {
                    if (i >= chars.length - 1) {
                        return null;
                    }
                    if (item.ch != chars[++i]) {
                        if (countOfChanges > 1) {
                            return null;
                        }
                        if (item.ch == '*' || chars[i] != '*') {
                            return getCorrectXml(replaceCharAtPosition(originalXml, chars[i], item.index), countOfChanges);
                        } else if (chars[i] == '*') {
                            return getCorrectXml(replaceCharAtPosition(originalXml, item.ch, i), countOfChanges);
                        }
                        return null;
                    }
                }

                if (stack.isEmpty()) {
                    return null;
                }

                if (chars[++i] != '>' || stack.pop().ch != '<') {
                    return null;
                }
            } else if (ch == '>') {
                tagStack.add(tag);
                stack.add(new CharIndex(ch, i));
            }
            i++;
        }

        if (stack.size() == 4 && tagStack.size() == 2) {
            return getCorrectXml(replaceCharAtPosition(originalXml, '/', tagStack.lastElement().get(0).index));
        }

        if (i == chars.length && stack.isEmpty() && tagStack.isEmpty()) {
            return originalXml;
        }

        return null;
    }

    private static String replaceCharAtPosition(String originalString, char ch, int position) {
        return originalString.substring(0, position) + ch + originalString.substring(position + 1);
    }


    record CharIndex(char ch, int index) {
    }
}