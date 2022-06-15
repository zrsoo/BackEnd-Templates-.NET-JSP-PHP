using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;
using Tv.Models;
using TvSubscription.Data;
using TvSubscription.Model;

namespace Tv.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly TvContext _context;

        public HomeController(ILogger<HomeController> logger, TvContext context)
        {
            _logger = logger;
            _context = context;
        }

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult Privacy()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }

        [HttpPost("login")]
        public IActionResult Login(String username)
        {
            Person person = _context.Persons.FirstOrDefault(person => person.Name == username);

            HttpContext.Session.SetString("username", person.Name);
            HttpContext.Session.SetInt32("id", person.Id);

            return Redirect("/");
        }
    }
}