namespace TvSubscription.Model;

public class Channel
{
    private int Id { get; set; }
    private int OwnerId { get; set; }
    private string Name { get; set; }
    private string Description { get; set; }
    private string Subscribers { get; set; }

    public Channel(int id, int ownerId, string name, string description, string subscribers)
    {
        Id = id;
        OwnerId = ownerId;
        Name = name;
        Description = description;
        Subscribers = subscribers;
    }
}
