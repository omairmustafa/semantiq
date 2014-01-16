package readibility;

import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

/**	EnglishSyllableCounter: Counts syllables in English words.
 *
 *	<p>
 *	This syllable counter uses a two-stage process for counting syllables
 *	in a word.
 *	</p>
 *
 *	<ol>
 *	<li>The word is first looked up in a dictionary which maps words
 *		to their syllable counts.  When the word is found in the
 *		dictionary, the associated syllable count is returned.
 *		</li>
 *	<li>When the word does not appear in the dictionary, the syllable
 *		count is computed from the number of vowel groups in the word.
 *		Adjustments are made for silent final "e"s and certain other
 *		letter groups.
 *	</ol>
 *
 *	<p>
 *	Most of the data used to construct the dictionary of syllable counts
 *	comes from a pronunciation dictionary provided by Carnegie-Mellon
 *	University.
 *	</p>
 *
 *	<p>
 *	The code used to compute syllable counts for words not found in the
 *	syllable counts dictionary is based upon that written by Greg Fast in
 *	Perl and Larry Ogrodnek in Java.  Their methods provide the correct
 *	syllable count about 85-90% of the time.  Rarely is the syllable
 *	count wrong by more than one.
 *	</p>
 */

public class EnglishSyllableCounter
{
	/**	Map of spellings to syllable counts. */

	protected Map syllableCountMap	=
		new HashMap();

	protected static final Pattern[] SubtractSyllables =
		new Pattern[]
		{
			Pattern.compile( "cial" ) ,
			Pattern.compile( "tia" ) ,
			Pattern.compile( "cius" ) ,
			Pattern.compile( "cious" ) ,
			Pattern.compile( "giu" ) ,	// belgium!
			Pattern.compile( "ion" ) ,
			Pattern.compile( "iou" )	,
			Pattern.compile( "sia$" ) ,
			Pattern.compile( ".ely$" )	// absolutely! (but not ely!)
		};

	protected static final Pattern[] AddSyllables =
		new Pattern[]
		{
			Pattern.compile( "ia" ),
			Pattern.compile( "riet" ),
			Pattern.compile( "dien" ),
			Pattern.compile( "iu" ),
			Pattern.compile( "io" ),
			Pattern.compile( "ii" ),
			Pattern.compile( "[aeiouym]bl$" ) ,		// -Vble, plus -mble
			Pattern.compile( "[aeiou]{3}" ) ,		// agreeable
			Pattern.compile( "^mc" ) ,
			Pattern.compile( "ism$" ) ,				// -isms
			Pattern.compile( "([^aeiouy])\1l$" ) ,	// middle twiddle battle bottle, etc.
			Pattern.compile( "[^l]lien" ) ,			// alien, salient [1]
			Pattern.compile( "^coa[dglx]." ) , 		// [2]
			Pattern.compile( "[^gq]ua[^auieo]" ) ,	// i think this fixes more than it breaks
			Pattern.compile( "dnt$" )				// couldn't
		};

	/**	Create an English syllable counter. */

	public EnglishSyllableCounter()
	{
	}

	/**	Load syllable counts map from a URL.
	 *
	 *	@param	mapURL		URL for map file.
	 *	@param 	separator	Field separator.
	 *	@param	qualifier	Quote character.
	 *	@param	encoding	Character encoding for the file.
	 *
	 *	@throws FileNotFoundException	If input file does not exist.
	 *	@throws IOException				If input file cannot be opened.
	 *
	 *	@return				Map with values read from file.
	 */



	/** Find number of syllables in a single English word.
	 *
	 *	@param	word	The word whose syllable count is desired.
	 *
	 *	@return			The number of syllables in the word.
	 */
	public static int countSyllables( String word )
	{
		int result = 0;
								//	Null or empty word?
								//	Syllable count is zero.
		if ( ( word == null ) || ( word.length() == 0 ) )
		{
			return result;
		}
								//	If word is in the dictionary,
								//	return the syllable count from the
								//	dictionary.

		String lcWord	= word.toLowerCase();

								//	If word is not in the dictionary,
								//	use vowel group counting to get
								//	the estimated syllable count.

								//	Remove embedded apostrophes and
								//	terminal e.

		lcWord	= lcWord.replaceAll( "'" , "" ).replaceAll( "e$" , "" );

								//	Split word into vowel groups.

		String[] vowelGroups	= lcWord.split( "[^aeiouy]+" );

								//	Handle special cases.

								//	Subtract from syllable count
								//	for these patterns.

			for ( Pattern p : SubtractSyllables )
			{
				Matcher m	= p.matcher( lcWord );

				if ( m.find() )
				{
					result--;
				}
			}
								//	Add to syllable count for these patterns.

			for ( Pattern p : AddSyllables )
			{
				Matcher m	= p.matcher( lcWord );

 				if ( m.find() )
				{
					result++;
				}
			}

			if ( lcWord.length() == 1 )
			{
				result++;
			}
								//	Count vowel groupings.

			if	(	( vowelGroups.length > 0 ) &&
					( vowelGroups[ 0 ].length() == 0 )
				)
			{
				result	+= vowelGroups.length - 1;
			}
			else
			{
				result	+= vowelGroups.length;
			}
		
								//	Return syllable count of
								//	at least one.

		return Math.max( result , 1 );
	}
}

/*
Copyright (c) 2008, 2009 by Northwestern University.
All rights reserved.

Developed by:
   Academic and Research Technologies
   Northwestern University
   http://www.it.northwestern.edu/about/departments/at/

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal with the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or
sell copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimers.

    * Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the following
      disclaimers in the documentation and/or other materials provided
      with the distribution.

    * Neither the names of Academic and Research Technologies,
      Northwestern University, nor the names of its contributors may be
      used to endorse or promote products derived from this Software
      without specific prior written permission.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE CONTRIBUTORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE.
*/