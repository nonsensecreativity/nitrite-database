/*
 *
 * Copyright 2017 Nitrite author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.dizitart.no2.fulltext.languages;

import org.dizitart.no2.fulltext.Language;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Afrikaans stop words
 *
 * @since 2.1.0
 * @author Anindya Chatterjee
 */
public class Afrikaans implements Language {
    @Override
    public Set<String> stopWords() {
        return new HashSet<>(Arrays.asList(
                "'n",
                "aan",
                "af",
                "al",
                "as",
                "baie",
                "by",
                "daar",
                "dag",
                "dat",
                "die",
                "dit",
                "een",
                "ek",
                "en",
                "gaan",
                "gesê",
                "haar",
                "het",
                "hom",
                "hulle",
                "hy",
                "in",
                "is",
                "jou",
                "jy",
                "kan",
                "kom",
                "ma",
                "maar",
                "met",
                "my",
                "na",
                "nie",
                "om",
                "ons",
                "op",
                "saam",
                "sal",
                "se",
                "sien",
                "so",
                "sy",
                "te",
                "toe",
                "uit",
                "van",
                "vir",
                "was",
                "wat",
                "ŉ"
        ));
    }
}
