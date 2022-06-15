using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Tv.Migrations
{
    public partial class first : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Channels",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false),
                    OwnerId = table.Column<int>(type: "int", nullable: false),
                    Name = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Description = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Subscribers = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Channels", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Persons",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false),
                    Name = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Age = table.Column<int>(type: "int", nullable: false),
                    Gender = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Persons", x => x.Id);
                });

            migrationBuilder.InsertData(
                table: "Channels",
                columns: new[] { "Id", "Description", "Name", "OwnerId", "Subscribers" },
                values: new object[,]
                {
                    { 1, "Fun kids", "Nickelodeon", 1, "Spitzila 10.03.2014" },
                    { 2, "Benga benga", "Weceu", 2, "Zaris 15.06.2022; Spitzila 15.06.2022; Olex 12.02.2001" },
                    { 3, "Nazi", "Mein Fuhrer", 3, "Spitzila 15.06.2022; Olex 12.02.2001" },
                    { 4, "Naruto", "Minimax", 1, "Olex 12.02.2001" }
                });

            migrationBuilder.InsertData(
                table: "Persons",
                columns: new[] { "Id", "Age", "Gender", "Name" },
                values: new object[,]
                {
                    { 1, 20, "male", "Zaris" },
                    { 2, 21, "male", "Olex" },
                    { 3, 21, "female", "Spitzila" }
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Channels");

            migrationBuilder.DropTable(
                name: "Persons");
        }
    }
}
