using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace TvSubscription.Model;

public class Person : IValidatableObject
{
    [DatabaseGenerated(DatabaseGeneratedOption.None)]
    public int Id { get; set; }
    public string Name { get; set; }
    public int Age { get; set; }
    public string Gender { get; set; }

    public IEnumerable<ValidationResult> Validate(ValidationContext validationContext)
    {
        if (Gender != "male" && Gender != "female")
            yield return new ValidationResult(
                "Gender cannot be something other than male or female.",
                new[] {nameof(Gender)});
    }
}
