program jaror;
uses crt;
var
  inputFile,outputFile: text;
  sor: string;
  ora, perc, mp: array[1..1000] of byte;
  rendszam: array[1..1000] of string;
  sorokszama,i,j,elozo: integer;
  busz,kamion,motor,auto: integer;
  hossz,vege: integer;
  keresett: string;
  jo: boolean;
  eltelt: integer;

function szam(szoveg: string):byte;
begin
  szam:=(ord(szoveg[1])-48)*10 + ord(szoveg[2])-48;
end;

function idomp(i: integer):longint;
begin
  idomp:=ora[i]*60*60+perc[i]*60+mp[i];
end;

function null(i: integer):string;
var
  egyes, tizes:byte;
begin
  egyes:=i mod 10;
  tizes:=i div 10;
  null:=concat(chr(48+tizes),chr(48+egyes));
end;

begin
  clrscr;
  assign(inputFile, 'jarmu.txt');
  reset(inputFile);

  sorokszama:=0;
  while (not eof(inputFile)) do
  begin
    readln(inputFile, sor);
    inc(sorokszama);
    ora[sorokszama]:=szam(copy(sor, 1, 2));
    perc[sorokszama]:=szam(copy(sor, 4, 2));
    mp[sorokszama]:=szam(copy(sor, 7, 2));
    rendszam[sorokszama]:=copy(sor, 10, 7);
  end;
  close(inputFile);
  writeln('Beolvasott sorok szama: ', sorokszama);

  // 2. feladat:
  writeln('2. feladat: ',ora[sorokszama]-ora[1]+1);

  // 3. feladat:
  writeln('3. feladat:');
  elozo:=-1;
  for i:=1 to sorokszama do
  begin
    if ora[i]>elozo then
    begin
      writeln(ora[i],'. ora: ',rendszam[i]);
      elozo:=ora[i];
    end;
  end;

  // 4. feladat:
  writeln('4. feladat:');
  busz:=0;
  kamion:=0;
  motor:=0;
  auto:=0;
  for i:=1 to sorokszama do
  begin
    case rendszam[i][1] of
      'B': inc(busz);
      'K': inc(kamion);
      'M': inc(motor);
    else
      inc(auto);
    end;
  end;
  writeln('Buszok: ', busz);
  writeln('Kamionok: ', kamion);
  writeln('Motorok: ', motor);
  writeln('Autok: ', auto);

  // 5. feladat:
  hossz:=0;
  vege:=0;
  for i:=2 to sorokszama do
  begin
    if idomp(i)-idomp(i-1) > hossz then
    begin
      hossz:=idomp(i)-idomp(i-1);
      vege:=i;
    end;
  end;
  writeln('5. feladat: ',null(ora[vege-1]),':',null(perc[vege-1]),':',null(mp[vege-1]),' - ',null(ora[vege]),':',null(perc[vege]),':',null(mp[vege]));

  // 6. feladat:
  writeln('6. feladat:');
  write('A keresett rendszam: ');
  readln(keresett);
  for i:=1 to sorokszama do
  begin
    jo:=true;
    for j:=1 to 7 do
    begin
      if (rendszam[i][j] <> keresett[j]) and (keresett[j] <> '*') then jo:=false;
    end;
    if jo then writeln(rendszam[i]);
  end;

  // 7. feladat:
  assign(outputFile, 'vizsgalt.txt');
  rewrite(outputFile);
  writeln('7. feladat:');
  eltelt:=5*60;
  for i:=1 to sorokszama do
  begin
    if i>1 then eltelt:=eltelt + idomp(i) - idomp(i-1);
    if eltelt >= 5*60 then
    begin
      writeln(outputFile, ora[i],' ',perc[i],' ',mp[i],' ',rendszam[i]);
      writeln(ora[i],' ',perc[i],' ',mp[i],' ',rendszam[i]);
      eltelt:=0;
    end;
  end;
  close(outputfile);

end.
