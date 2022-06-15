using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using TvSubscription.Data;
using TvSubscription.Model;

namespace Tv.Controllers
{
    public class ChannelsController : Controller
    {
        private readonly TvContext _context;

        public ChannelsController(TvContext context)
        {
            _context = context;
        }

        // GET: Channels
        public async Task<IActionResult> Index(string searchString)
        {
            /*return _context.Channels != null ? 
                        View(await _context.Channels.ToListAsync()) :
                        Problem("Entity set 'TvContext.Channels'  is null.");*/

            ViewData["CurrentFilter"] = searchString;
            ViewBag.ShowSubscription = false;

            if (!string.IsNullOrEmpty(searchString))
            {
                Person? person = await _context.Persons.FirstOrDefaultAsync(person => person.Name == searchString);

                if (person != null)
                {
                    var channels = _context.Channels.Where(channel => channel.OwnerId == person.Id);
                    return View(await channels.ToListAsync());
                }
            }

            return View(await _context.Channels.ToListAsync());
        }

        public async Task<IActionResult> ViewSubscriptions()
        {
            ViewBag.ShowSubscription = true;

            var name = HttpContext.Session.GetString("username");

            if (name != null)
            {
                var channels = _context.Channels.Where(channel => channel.Subscribers.Contains(name));
                return View(await channels.ToListAsync());
            }

            return View(await _context.Channels.ToListAsync());
        }

        // GET: Channels/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Channels == null)
            {
                return NotFound();
            }

            var channel = await _context.Channels
                .FirstOrDefaultAsync(m => m.Id == id);
            if (channel == null)
            {
                return NotFound();
            }

            return View(channel);
        }

        // GET: Channels/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Channels/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,OwnerId,Name,Description,Subscribers")] Channel channel)
        {
            if (ModelState.IsValid)
            {
                _context.Add(channel);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(channel);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Subscribe(int id)
        {
            var name = HttpContext.Session.GetString("username");
            var channel = await _context.Channels.FirstOrDefaultAsync(channel => channel.Id == id);

            if (channel == null || name == null)
                return RedirectToAction(nameof(Index));

            if (channel.Subscribers.Contains(name))
            {
                return RedirectToAction(nameof(Index));
            }

            channel.Subscribers += name += DateTime.Now;
            await _context.SaveChangesAsync();

            return RedirectToAction(nameof(Index));
        }

        // GET: Channels/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Channels == null)
            {
                return NotFound();
            }

            var channel = await _context.Channels.FindAsync(id);
            if (channel == null)
            {
                return NotFound();
            }
            return View(channel);
        }

        // POST: Channels/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,OwnerId,Name,Description,Subscribers")] Channel channel)
        {
            if (id != channel.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(channel);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ChannelExists(channel.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(channel);
        }

        // GET: Channels/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Channels == null)
            {
                return NotFound();
            }

            var channel = await _context.Channels
                .FirstOrDefaultAsync(m => m.Id == id);
            if (channel == null)
            {
                return NotFound();
            }

            return View(channel);
        }

        // POST: Channels/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Channels == null)
            {
                return Problem("Entity set 'TvContext.Channels'  is null.");
            }
            var channel = await _context.Channels.FindAsync(id);
            if (channel != null)
            {
                _context.Channels.Remove(channel);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool ChannelExists(int id)
        {
          return (_context.Channels?.Any(e => e.Id == id)).GetValueOrDefault();
        }
    }
}
