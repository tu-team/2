# Preliminary words split design specification.

## Entry criteria

Goal is set to Preprocess.

## Exit criteria

Text of the issue is separated into sentences and words.

## Input

Plain text of issue.

## Output

Words and sentences. 
Words are grouped into sentences, RelEx require text split into sentences.

## Workflow

  1. System Extracts special words, i.e. to separate: URLs, network and file system addresses, e-mails, phone numbers, IPs.
  1. System fixes misprints with auto corrector.
  1. System splits text into sentences with DocSplit script from RelEx.
  1. System splits sentences into words with RelEx.
