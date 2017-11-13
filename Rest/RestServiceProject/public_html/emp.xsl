<?xml version="1.0" encoding="UTF-8"?>
 <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
version="1.0" >
     <xsl:template match="emps">
         <emps>
           <xsl:for-each select="emp">
               <emp>
                   <exp><xsl:value-of select="exp"/></exp>
                   <id><xsl:value-of select="id"/></id>
                   <name><xsl:value-of select="name"/></name>
                   <salary><xsl:value-of select="salary*exp"/></salary>
               </emp>
           </xsl:for-each>
         </emps>

     </xsl:template>
 </xsl:stylesheet>
