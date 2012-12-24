using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using TU.Application;
using TU.Application.Dto;

namespace TU.Web.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        //[ChildActionOnly]
        //public ActionResult RequestDashboard()
        //{
        //    return View();
        //}

        //[ChildActionOnly]
        //[HttpPost]
        //public ActionResult RequestDashboard(Request request)
        //{
        //    _requestService.CreateRequest(request);
        //    return View(request);
        //}

        //[ChildActionOnly]
        //public ActionResult RequestHistoryDashboard()
        //{
        //    return View();
        //}
    }
}
