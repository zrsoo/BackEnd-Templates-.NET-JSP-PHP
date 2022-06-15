using Microsoft.EntityFrameworkCore;
using TvSubscription.Model;

namespace TvSubscription.Data;

public class TvContext : DbContext
{
    public TvContext(DbContextOptions<TvContext> options) : base (options)
    {
    }

    public DbSet<Channel> Channels { get; set; }
    public DbSet<Person> Persons { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Person>().HasData(
            new Person { Id = 1, Name = "Zaris", Age = 20, Gender = "male" },
            new Person { Id = 2, Name = "Olex", Age = 21, Gender = "male" },
            new Person { Id = 3, Name = "Spitzila", Age = 21, Gender = "female" }
            );

        modelBuilder.Entity<Channel>().HasData(
            new Channel { Id = 1, OwnerId = 1, Name = "Nickelodeon", 
                Description = "Fun kids", Subscribers = "Spitzila 10.03.2014" },
            new Channel { Id = 2, OwnerId = 2, Name = "Weceu", 
                Description = "Benga benga", Subscribers = "Zaris 15.06.2022; Spitzila 15.06.2022; Olex 12.02.2001" },
            new Channel { Id = 3, OwnerId = 3, Name = "Mein Fuhrer", 
                Description = "Nazi", Subscribers = "Spitzila 15.06.2022; Olex 12.02.2001" },
            new Channel { Id = 4, OwnerId = 1, Name = "Minimax",
                Description = "Naruto", Subscribers = "Olex 12.02.2001" }
            );
    }
}
