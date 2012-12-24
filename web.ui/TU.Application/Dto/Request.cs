using System.Collections.Generic;

namespace TU.Application.Dto
{
    public class Request
    {
        public string Uid { get; set; }
        public RequestType Type { get; set; }
        public string Name { get; set; }
        public IList<Message> Messages { get; set; }
        public RequestState State { get; set; }
    }
}