using System;
using System.Collections.Generic;

namespace TU.Domain.Entities
{
    public class Request : EntityBase
    {
        public string RequestId { get; set; }
        public RequestType RequestType { get; set; }
        public string RequestState { get; set; }
        public DateTime RequestDate { get; set; }
        public IList<Message> Messages { get; set; }
        //public Guid UserId { get; set; }
    }

    public class Message : EntityBase
    {
        public Request Request { get; set; }
        public TimeSpan Time { get; set; }
        public string Text { get; set; }
        public MessageType Type { get; set; }
    }

    public enum MessageType
    {
        Incoming,
        Outgoing
    }
}