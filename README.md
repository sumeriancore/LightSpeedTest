<h5>SQL query parser test task for Ecwid by Lightspeed</h5>

<h5>You may have a couple of questions, for example:
Why do we have two parser implementations?

Analyzing the requirements for this task, I did not find an answer - do we always have a formatted query or can it be written on the knee)

In case we have an unformatted query, then the first thing that came to my mind was to use regular expressions. However, I do not really like this approach, because regular expressions sometimes cause performance problems. On the other hand, we get a solution that can work for both a crooked query (syntactically) and a formatted one. And if we have problems with parsing an SQL query using a regular expression, then most likely we have some inadequately large/crooked and some other query (this is my assumption)

As a result, I decided to implement the task with two inputs: for a formatted query and an unformatted one. Naturally, the algorithm for a formatted query is not suitable for solving the problem for an unformatted one, at least my approach, but the solution with regular expressions allows you to parse both queries, both formatted and unformatted.</h5>