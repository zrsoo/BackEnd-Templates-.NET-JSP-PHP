using Microsoft.EntityFrameworkCore;
using TvSubscription.Model;

namespace TvSubscription.Data;

public class SubscriptionContext : DbContext
{
    public SubscriptionContext(DbContextOptions<SubscriptionContext> options) : base (options)
    {
    }

    public DbSet<Channel> Channels { get; set; }
    public DbSet<Person> Persons { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Person>().HasData(
            new Person(1, "Zaris", 20, "male"),
            new Person(2, "Olex", 21, "male"),
            new Person(3, "Spitzila", 21, "female")
            );

        modelBuilder.Entity<Channel>().HasData(
            new Channel(1, 1, "Nickelodeon", "Fun kids", "Spitzila 10.03.2014"),
            new Channel(2, 2, "Weceu", "Benga benga", "Zaris 15.06.2022; Spitzila 15.06.2022; Olex 12.02.2001"),
            new Channel(3, 3, "Mein Fuhrer", "Nazi", "Spitzila 15.06.2022; Olex 12.02.2001"),
            new Channel(4, 1, "Minimax", "Naruto", "Olex 12.02.2001")
            );
    }
}
