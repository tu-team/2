# Text auto correct
Should check and correct text

There is no complite solution which can automatically check and correct spelling. all availlable libs can only give you a list of suitable corrections.

Tests were made in Libre office which uses Aspell and in Abiword which uses Enchant library


## Results:

<Table>
<tr><td></td> <td>[Libre Office](http://www.libreoffice.org/)</td> <td>[Abiword](http://www.abisource.com/projects/link-grammar/)</td><td>[Jazzy](http://jazzy.sourceforge.net/)</td></tr>
<tr><td> found errors </td> <td> 8</td> <td> 5</td><td> 19</td> </tr>
<tr> <td> not found errors</td> <td> 2</td> <td> 7</td> <td> ?</td></tr>
<tr> <td> framework mistakes </td><td>10 </td> <td> 8</td> <td> 8</td></tr>
<tr> <td> relevance of the proposed changes</td> <td> 30% </td> <td> 70% </td> <td> 15% </td></tr>
</table>

All products have option to add dictionaries

Currently [Jazzy](http://jazzy.sourceforge.net/) could be recommended as most effective auto-corrector.

## Excluded products
I did not test Kword because of many bugs, and OpenCog because it uses only Link Grammar which is included in AbiWord and can't get better results than AbiWord.


