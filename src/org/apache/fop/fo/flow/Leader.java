/*-- $Id$ --

 ============================================================================
                   The Apache Software License, Version 1.1
 ============================================================================

    Copyright (C) 1999 The Apache Software Foundation. All rights reserved.

 Redistribution and use in source and binary forms, with or without modifica-
 tion, are permitted provided that the following conditions are met:

 1. Redistributions of  source code must  retain the above copyright  notice,
    this list of conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 3. The end-user documentation included with the redistribution, if any, must
    include  the following  acknowledgment:  "This product includes  software
    developed  by the  Apache Software Foundation  (http://www.apache.org/)."
    Alternately, this  acknowledgment may  appear in the software itself,  if
    and wherever such third-party acknowledgments normally appear.

 4. The names "FOP" and  "Apache Software Foundation"  must not be used to
    endorse  or promote  products derived  from this  software without  prior
    written permission. For written permission, please contact
    apache@apache.org.

 5. Products  derived from this software may not  be called "Apache", nor may
    "Apache" appear  in their name,  without prior written permission  of the
    Apache Software Foundation.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS  FOR A PARTICULAR  PURPOSE ARE  DISCLAIMED.  IN NO  EVENT SHALL  THE
 APACHE SOFTWARE  FOUNDATION  OR ITS CONTRIBUTORS  BE LIABLE FOR  ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL  DAMAGES (INCLU-
 DING, BUT NOT LIMITED TO, PROCUREMENT  OF SUBSTITUTE GOODS OR SERVICES; LOSS
 OF USE, DATA, OR  PROFITS; OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON
 ANY  THEORY OF LIABILITY,  WHETHER  IN CONTRACT,  STRICT LIABILITY,  OR TORT
 (INCLUDING  NEGLIGENCE OR  OTHERWISE) ARISING IN  ANY WAY OUT OF THE  USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 This software  consists of voluntary contributions made  by many individuals
 on  behalf of the Apache Software  Foundation and was  originally created by
 James Tauber <jtauber@jtauber.com>. For more  information on the Apache
 Software Foundation, please see <http://www.apache.org/>.

 */
package org.apache.fop.fo.flow;

// FOP
import org.apache.fop.fo.*;
import org.apache.fop.fo.properties.*;
import org.apache.fop.datatypes.*;
import org.apache.fop.layout.Area;
import org.apache.fop.layout.BlockArea;
import org.apache.fop.layout.inline.LeaderArea;
import org.apache.fop.layout.FontState;
import org.apache.fop.apps.FOPException;
import org.apache.fop.messaging.MessageHandler;

/**
 * Implements fo:leader; main property of leader leader-pattern.
 * The following patterns are treated: rule, space, dots.
 * The pattern use-content is ignored, i.e. it still must be implemented.
 */

public class Leader extends FObjMixed {

    public static class Maker extends FObj.Maker {
        public FObj make(FObj parent,
                         PropertyList propertyList) throws FOPException {
            return new Leader(parent, propertyList);
        }
    }

    public static FObj.Maker maker() {
        return new Leader.Maker();
    }

    public Leader(FObj parent, PropertyList propertyList) {
        super(parent, propertyList);
        this.name = "fo:leader";
    }

    public Status layout(Area area) throws FOPException {
        BlockArea blockArea;
        //restriction in this version
        if (!(area instanceof BlockArea)) {
            MessageHandler.errorln("WARNING: in this version of Fop fo:leader must be a direct child of fo:block ");
            return new Status(Status.OK);
        } else {
            blockArea = (BlockArea) area;
        }

        //retrieving font property information for fo:leader
        String fontFamily = this.properties.get("font-family").getString();
        String fontStyle = this.properties.get("font-style").getString();
        String fontWeight = this.properties.get("font-weight").getString();
        int fontSize =
          this.properties.get("font-size").getLength().mvalue();
        //wrapping it up into Fontstate
        FontState fontstate = new FontState(area.getFontInfo(), fontFamily,
                                            fontStyle, fontWeight, fontSize);
        //color properties
        ColorType c = this.properties.get("color").getColorType();
        float red = c.red();
        float green = c.green();
        float blue = c.blue();

        //fo:leader specific properties
        //determines the pattern of leader; allowed values: space, rule,dots, use-content
        int leaderPattern = this.properties.get("leader-pattern").getEnum();
        //length of the leader
        int leaderLengthOptimum = this.properties.get(
                                    "leader-length.optimum").getLength().mvalue();
        int leaderLengthMinimum = this.properties.get(
                                    "leader-length.minimum").getLength().mvalue();
        int leaderLengthMaximum = this.properties.get(
                                    "leader-length.maximum").getLength().mvalue();
        //the following properties only apply for leader-pattern = "rule"
        int ruleThickness = this.properties.get(
                              "rule-thickness").getLength().mvalue();
        int ruleStyle = this.properties.get("rule-style").getEnum();
        // if leaderPatternWidth = 0 = default = use-font-metric
        int leaderPatternWidth = this.properties.get(
                                   "leader-pattern-width").getLength().mvalue();
        int leaderAlignment =
          this.properties.get("leader-alignment").getEnum();

        // initialize id
        String id = this.properties.get("id").getString();
        blockArea.getIDReferences().initializeID(id, blockArea);

        //adds leader to blockarea, there the leaderArea is generated
        int succeeded = blockArea.addLeader(fontstate, red, green, blue,
                                            leaderPattern, leaderLengthMinimum,
                                            leaderLengthOptimum, leaderLengthMaximum,
                                            ruleThickness, ruleStyle, leaderPatternWidth,
                                            leaderAlignment);
        if (succeeded == 1) {
            return new Status(Status.OK);
        } else {
            //not sure that this is the correct Status here
            return new Status(Status.AREA_FULL_SOME);
        }
    }

    /* //should only be necessary for use-content
        protected void addCharacters(char data[], int start, int length) {
            FOText textNode = new FOText(data,start,length, this);
            children.addElement(textNode);
        }
      */


}
