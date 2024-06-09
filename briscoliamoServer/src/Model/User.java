package Model;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Objects;

public class User
{
    private final PrintStream streamOut;
    private final InputStream streamIn;
    private final String nickname;

    public User(Socket client, String name)
    {
        try
        {
            this.streamOut = new PrintStream(client.getOutputStream());
            this.streamIn = client.getInputStream();
            this.nickname = name;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public PrintStream getOutStream()
    {
        return this.streamOut;
    }

    public InputStream getInputStream()
    {
        return this.streamIn;
    }

    public String getNickname()
    {
        return this.nickname;
    }

    public String toString() {return this.getNickname();}
}
