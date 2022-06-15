using System.ComponentModel.DataAnnotations;

namespace TvSubscription.Model;

public class Person : IValidatableObject
{
    private int Id { get; set; }
    private string Name { get; set; }
    private int Age { get; set; }
    private string Gender { get; set; }

    public Person(int id, string name, int age, string gender)
    {
        Id = id;
        Name = name;
        Age = age;
        Gender = gender;
    }

    public IEnumerable<ValidationResult> Validate(ValidationContext validationContext)
    {
        if (Gender != "male" || Gender != "female")
            yield return new ValidationResult(
                "Gender cannot be something other than male or female",
                new[] {nameof(Gender)});
    }
}
