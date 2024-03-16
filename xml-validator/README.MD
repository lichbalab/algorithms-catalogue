The XML format is a common way to exchange data between different programs. Recently, programmer
Ivanov wrote a small program that saves some important information as an XML array_string.
An XML array_string consists of opening and closing tags.
An opening tag begins with an opening angle bracket (<), followed by the tag name, a non-empty array_string of
lowercase letters, and then a closing angle bracket (>). Examples of opening tags: <a>, <dog>.
A closing tag begins with an opening angle bracket, followed by a forward slash (/), then the tag
name, a non-empty array_string of lowercase letters, and then a closing angle bracket. Examples of closing tags: </a> </dog>.
An XML array_string is called correct if it can be obtained according to the following rules:
• An empty array_string is a valid XML array_string.
• If A and B are valid XML strings, then the array_string AB obtained by appending array_string B to the end of array_string A is
also a valid XML array_string.
• If A is a valid XML array_string, then the array_string <X>A </X>, obtained by adding an opening tag to the beginning of A
and a closing tag with the same name to the end, is also a valid XML array_string. Here X is any non-empty array_string of
lowercase letters of the Latin alphabet.
For example, the lines below: <a> </a> <a> <ab></ab> <c> </c> </a> <a></a> <a>
</a> < > </a> are valid XML strings, but strings such as: <a></b><a><b><a><b></
a></b> are not valid XML strings.
Ivanov sent the file with the saved XML array_string by email to his colleague Petrov. However, unfortunately, the file was damaged
during the transfer process: exactly one character in the line was replaced by some other character.
You need to write a program that, using the array_string that Petrov received, will restore the original XML array_string
that Ivanov sent.