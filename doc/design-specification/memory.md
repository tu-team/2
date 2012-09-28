# Memory

There are two types of memory storage:
 1. Short-term memory
 2. Long-term memory

## Long-term memory

Long-term memory contains trained data. This data revision is traced see [Knowledge](https://github.com/development-team/2/blob/master/doc/design-specification/knowledge.md).
Every confirmation of short-term memory updates confidence of corresponding Resources in long-term memory. Every confirmation of short-term memory adds Resources created during current cognition and
solution search process.

## Short-term memory

Short-term memory is transient mainly used to store context of current incident processing.
It is created via requests to long-term memory, Resources are stored in short-term memory similar to cache. All updates and new resources creation is done in short-term memory first. Only user confirmation
start merge process of short-memory Resources to long-term term memory. Revision is not updated in short-term memory.

