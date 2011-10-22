#!/bin/bash

# This script should generate from Kune strings db translations to GWT I18n .java resources

usage() {
    echo "Use: $0 -l langcode"
    echo "$0 -l en [-j]"
}

while getopts “hl:j” OPTION
do
    case $OPTION in
	h)
            usage
            exit 1
            ;;
	l)
	    L=$OPTARG
	    ;;
	j)
	    J=1
	    ;;
	?)
            usage
            exit
            ;;
    esac
done	

if [[ -z $L ]]
then
    usage
    exit 1
fi
if [[ $L = "en" ]]
then
  SEL="SELECT g.tr_key,g.tr_key,g.text,l.code,g.noteForTranslators FROM globalize_translations g, globalize_languages l where g.language_id = l.id AND l.code='en';"
else 
  SEL="SELECT g.tr_key,p.tr_key,g.text,l.code,g.noteForTranslators FROM globalize_translations g, globalize_translations p, globalize_languages l where g.language_id = l.id AND l.code='$L' AND (g.parent_id = p.id OR g.parent_id = NULL) AND g.text != '';"
fi

if [[ $J -eq 1 ]] 
then
mysql -B --skip-column-names --default-character-set=utf8 --password="db4kune" -u kune kune_dev -e "$SEL" \
| sed 's/	/ł/g' \
| awk -F "ł" '
BEGIN {
  print "/**"
  print " * Autogenerated by trunk/bin/i18n-db2gwt.sh, please not edit directly"
  print " */"
  print "public interface KuneConstants extends ConstantsWithLookup {"
}
{
  lang=$4
  nt=$5
  if (lang != "en") {
    trkey=$2
    trad=$3
  } else {
    trkey=$1
    trad=$2
  }

  cmd="echo \"  String \"`bin/convertI18nMsgToMethods.sh \""trkey" "nt"\"`\"();\""
  result = system(cmd)
}
END {
  print "}"
}'
else
mysql -B --skip-column-names --default-character-set=utf8 --password="db4kune" -u kune kune_dev -e "$SEL" \
| sed 's/	/ł/g' \
| awk -F "ł" '
BEGIN {
  print "#"
  print "# Autogenerated by trunk/bin/i18n-db2gwt.sh, please not edit directly"
  print "#"
  print
}
{
  lang=$4
  nt=$5
  if (lang != "en") {
    trkey=$2
    trad=$3
  } else {
    trkey=$1
    trad=$2
  }
  #gsub(/\[/, "\\[", trkey)
  #gsub(/\]/, "\\]", trkey)
  #print trkey "--->" trad
  cmd="LC_CTYPE=UTF-8 echo `bin/convertI18nMsgToMethods.sh \""trkey" "nt"\"` = \""trad"\""
  #print cmd
  #exit
  result = system(cmd)
}
END {
}'

fi
