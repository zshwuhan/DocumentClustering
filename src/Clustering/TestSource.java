// Copyright (c) Gratian Lup. All rights reserved.
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
//     * Redistributions of source code must retain the above copyright
//       notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above
//       copyright notice, this list of conditions and the following
//       disclaimer in the documentation and/or other materials provided
//       with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package Clustering;
import java.io.*;

public final class TestSource implements IDocumentSource {
    private String file_;
    private BufferedReader stream_;
    private String[] sentences_;
    private String[] words_;
    private int sentencePosition_;
    private int wordPosition_;

    public TestSource(String file) throws IOException {
        file_ = file;
        stream_ = new BufferedReader(new FileReader(file));
    }

    public boolean HasDocument() {
        try {
            return stream_.ready();
        }
        catch(IOException e) {
            return false;
        }
    }

    public boolean HasSentence() {
        if(sentences_ == null) {
            if(HasDocument() == false) {
                return false;
            }
            
            ReadLine();
            return (sentences_ != null) && (sentences_.length > 0);
        }

        if(sentencePosition_ >= sentences_.length) {
            sentences_ = null;
            return false;
        }
        else {
            return true;
        }
    }

    public boolean HasWord() {
        if(words_ == null) {
            if(HasSentence() == false) {
                return false;
            }
            
            ReadSentence();
            return (words_ != null) && (words_.length > 0);
        }

        if(wordPosition_ >= words_.length) {
            words_ = null;
            return false;
        }
        else {
            return true;
        }
    }

    public String NextWord() {
        return words_[wordPosition_++];
    }

    private void ReadLine() {
        try {
            // Imparte linia in propozitii (delimitat de '.').
            String line = stream_.readLine();
            sentences_ = line.split("[.]");
            sentencePosition_ = 0;
        }
        catch(IOException e) {
            sentences_ = null;
        }
    }

    private void ReadSentence() {
        String sentence = sentences_[sentencePosition_++];
        words_ = sentence.split("\\s");
        wordPosition_ = 0;
    }
}