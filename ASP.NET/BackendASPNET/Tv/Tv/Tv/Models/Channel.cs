using System.ComponentModel.DataAnnotations.Schema;

namespace TvSubscription.Model;

public class Channel
{
    [DatabaseGenerated(DatabaseGeneratedOption.None)]
    public int Id { get; set; }
    public int OwnerId { get; set; }
    public string Name { get; set; }
    public string Description { get; set; }
    public string Subscribers { get; set; }
}
