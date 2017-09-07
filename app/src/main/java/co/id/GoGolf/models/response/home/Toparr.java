package co.id.GoGolf.models.response.home;

/**
 * Created by dedepradana on 7/28/16.
 */
public class Toparr {
    private String position;

    private String title;

    private String event;

    private Request request;

    private String image;

    private String bgcolor;

    private String destination;

    public String getPosition ()
    {
        return position;
    }

    public void setPosition (String position)
    {
        this.position = position;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getEvent ()
    {
        return event;
    }

    public void setEvent (String event)
    {
        this.event = event;
    }

    public Request getRequest ()
    {
        return request;
    }

    public void setRequest (Request request)
    {
        this.request = request;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getBgcolor ()
    {
        return bgcolor;
    }

    public void setBgcolor (String bgcolor)
    {
        this.bgcolor = bgcolor;
    }

    public String getDestination ()
    {
        return destination;
    }

    public void setDestination (String destination)
    {
        this.destination = destination;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [position = "+position+", title = "+title+", event = "+event+", request = "+request+", image = "+image+", bgcolor = "+bgcolor+", destination = "+destination+"]";
    }
}
