using System;
using System.Collections.Generic;
using TU.Application.Dto;

namespace TU.Application
{
    public interface IRequestService
    {
        void CreateRequest(Request request);
        void SendMessage(Message message);
        //void SendRequestAsync(Request request);
        IEnumerable<Request> GetAllRequests();
        Request GetRequestById(Guid id);
        Request GetRequestByRequestId(string requestId);
    }
}