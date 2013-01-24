using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using TU.Application;
using TU.Application.Dto;

namespace TU.Web.Controllers
{
    public class RequestController : ApiController
    {
        private readonly IRequestService _requestService;

        public RequestController(IRequestService requestService)
        {
            _requestService = requestService;
        }

        // GET api/request
        public IEnumerable<Request> Get()
        {
            List<Request> requests = new List<Request>
                                         {
                                             new Request
                                                 {
                                                     Name = "Firefox",
                                                     Type = RequestType.Operation,
                                                     Uid = "ASDF-123",
                                                     State = RequestState.Done,
                                                     Messages = new List<Message>
                                                                    {
                                                                        new Message
                                                                            {
                                                                                DateTime =
                                                                                    DateTime.ParseExact(
                                                                                        "2012-12-20 10:10",
                                                                                        "yyyy-MM-dd HH:mm",
                                                                                        CultureInfo.CurrentCulture),
                                                                                Direction = MessageDirection.Outgoing,
                                                                                Order = 1,
                                                                                Text = "I have issues with firefox"
                                                                            },
                                                                        new Message
                                                                            {
                                                                                DateTime =
                                                                                    DateTime.ParseExact(
                                                                                        "2012-12-20 10:11",
                                                                                        "yyyy-MM-dd HH:mm",
                                                                                        CultureInfo.CurrentCulture),
                                                                                Direction = MessageDirection.Incoming,
                                                                                Order = 2,
                                                                                Text = "Start working..."
                                                                            },
                                                                        new Message
                                                                            {
                                                                                DateTime =
                                                                                    DateTime.ParseExact(
                                                                                        "2012-12-20 10:12",
                                                                                        "yyyy-MM-dd HH:mm",
                                                                                        CultureInfo.CurrentCulture),
                                                                                Direction = MessageDirection.Incoming,
                                                                                Order = 3,
                                                                                Text = "I do not jnow what is firefox"
                                                                            },
                                                                        new Message
                                                                            {
                                                                                DateTime =
                                                                                    DateTime.ParseExact(
                                                                                        "2012-12-20 10:12",
                                                                                        "yyyy-MM-dd HH:mm",
                                                                                        CultureInfo.CurrentCulture),
                                                                                Direction = MessageDirection.Outgoing,
                                                                                Order = 4,
                                                                                Text = "Firefox is a browser"
                                                                            },
                                                                        new Message
                                                                            {
                                                                                DateTime =
                                                                                    DateTime.ParseExact(
                                                                                        "2012-12-20 10:15",
                                                                                        "yyyy-MM-dd HH:mm",
                                                                                        CultureInfo.CurrentCulture),
                                                                                Direction = MessageDirection.Incoming,
                                                                                Order = 5,
                                                                                Text =
                                                                                    "I have a result for you: Install Mozilla Firefox Solution"
                                                                            }
                                                                    }
                                                 },
                                             new Request
                                                 {
                                                     Name = "Opera",
                                                     Type = RequestType.Operation,
                                                     Uid = "ZXCV-789",
                                                     State = RequestState.InProgress,
                                                     Messages = new List<Message>
                                                                    {
                                                                        new Message
                                                                            {
                                                                                DateTime =
                                                                                    DateTime.ParseExact(
                                                                                        "2012-12-20 10:10",
                                                                                        "yyyy-MM-dd HH:mm",
                                                                                        CultureInfo.CurrentCulture),
                                                                                Direction = MessageDirection.Outgoing,
                                                                                Order = 1,
                                                                                Text = "I have issues with opera"
                                                                            },
                                                                        new Message
                                                                            {
                                                                                DateTime =
                                                                                    DateTime.ParseExact(
                                                                                        "2012-12-20 10:11",
                                                                                        "yyyy-MM-dd HH:mm",
                                                                                        CultureInfo.CurrentCulture),
                                                                                Direction = MessageDirection.Incoming,
                                                                                Order = 2,
                                                                                Text = "Start working..."
                                                                            },
                                                                        new Message
                                                                            {
                                                                                DateTime =
                                                                                    DateTime.ParseExact(
                                                                                        "2012-12-20 10:12",
                                                                                        "yyyy-MM-dd HH:mm",
                                                                                        CultureInfo.CurrentCulture),
                                                                                Direction = MessageDirection.Incoming,
                                                                                Order = 3,
                                                                                Text = "I do not jnow what is opera"
                                                                            },
                                                                        new Message
                                                                            {
                                                                                DateTime =
                                                                                    DateTime.ParseExact(
                                                                                        "2012-12-20 10:12",
                                                                                        "yyyy-MM-dd HH:mm",
                                                                                        CultureInfo.CurrentCulture),
                                                                                Direction = MessageDirection.Outgoing,
                                                                                Order = 4,
                                                                                Text = "Opera is a browser"
                                                                            },
                                                                        new Message
                                                                            {
                                                                                DateTime =
                                                                                    DateTime.ParseExact(
                                                                                        "2012-12-20 10:15",
                                                                                        "yyyy-MM-dd HH:mm",
                                                                                        CultureInfo.CurrentCulture),
                                                                                Direction = MessageDirection.Incoming,
                                                                                Order = 5,
                                                                                Text =
                                                                                    "I have a result for you: Install Opera"
                                                                            }
                                                                    }
                                                 }
                                         };

            return requests;
        }

        // GET api/request/5
        public string Get(int id)
        {
            return "value";
        }

        // POST api/request
        public void Post([FromBody]string value)
        {
        }

        // PUT api/request/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/request/5
        public void Delete(int id)
        {
        }
    }
}
